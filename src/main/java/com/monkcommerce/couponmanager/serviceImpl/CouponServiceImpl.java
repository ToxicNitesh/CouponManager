package com.monkcommerce.couponmanager.serviceImpl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.monkcommerce.couponmanager.dto.Cart;
import com.monkcommerce.couponmanager.dto.Condition;
import com.monkcommerce.couponmanager.dto.CouponDto;
import com.monkcommerce.couponmanager.dto.ProductDto;
import com.monkcommerce.couponmanager.entity.Coupon;
import com.monkcommerce.couponmanager.entity.CouponType;
import com.monkcommerce.couponmanager.repository.CouponRepository;
import com.monkcommerce.couponmanager.repository.CouponTypeRepository;
import com.monkcommerce.couponmanager.service.CouponService;
import com.monkcommerce.couponmanager.util.AppConstants;
import com.monkcommerce.couponmanager.util.CouponUtil;

import jakarta.transaction.Transactional;

@Service
public class CouponServiceImpl implements CouponService {

	private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);

	@Autowired
	CouponRepository couponRepository;

	@Autowired
	CouponTypeRepository couponTypeRepository;

	@Autowired
	CouponUtil couponUtil;

	@Override
	public List<Coupon> getAllCoupons() {
		// TODO Auto-generated method stub
		return couponRepository.findAll();
	}

	@Transactional
	@Override
	public Map<String, String> createCoupon(CouponDto couponDto) {
		Gson json = new Gson();
		Map<String, String> response = new HashMap<>();
		boolean isUpdate = false;
		logger.info("createCoupon start");
		try {
			Coupon coupon = new Coupon();
			// considering only one type of coupon can be added
			Map<Boolean,String> isValid=validateCondition(couponDto);
			if (isValid.containsKey(true)) {
				if (couponDto.getId() != null) {
					logger.info("Updating the coupon");
					isUpdate = true;
					coupon.setLogonUpd(couponDto.getLogonAdd());
					coupon.setCouponId(couponDto.getId());
					coupon.setTimeStampUpd(new Timestamp(Instant.now().toEpochMilli()));
				} else {
					logger.info("Creating a coupon");
					coupon.setLogonAdd(couponDto.getLogonAdd());
					coupon.setTimeStampAdd(new Timestamp(Instant.now().toEpochMilli()));
				}
				if (couponDto.getType() != null) {
					// check if we already have this type
					logger.info("checking we already have that coupon type");
					CouponType cp = couponTypeRepository.findByCouponType(couponDto.getType());
					if (cp == null) {
						logger.info("creating new coupon type as " + couponDto.getType());
						cp = couponTypeRepository.save(new CouponType(couponDto.getType()));
					}
					// else add a new record in type table and set the coupon type to int

					coupon.setCouponCode(couponDto.getCode());
					coupon.setCouponType(cp.getId());

					coupon.setCondition(json.toJson(couponDto.getConditon()));
					coupon.setActive(AppConstants.YES);
					coupon.setExpDate(couponDto.getTo());
					coupon.setStrtDate(couponDto.getFrom());
					logger.info("Saving Coupon");
					couponRepository.save(coupon);
				}
				response.put(isUpdate ? AppConstants.UPDATE : AppConstants.CREATE, AppConstants.SUCCESS);
			}else {
				response.put(AppConstants.FAIL, isValid.get(false));
			}
		} catch (Exception e) {
			// TODO: handle exception
			response.put(AppConstants.FAIL, e.getMessage());
		}
		return response;
	}

	private Map<Boolean,String> validateCondition(CouponDto couponDto) {
		// TODO Auto-generated method stub
		Map<Boolean,String> response=new HashMap<>();
		response.put(true, null);
		/*
		 * if the type of coupon is BOGO then
		 * 		if all the free products are present in the application then
		 * 				get the price of each product and append to the productDto
		 * 		else
		 * 			return coupon is invalid since product is not present
		 * 
		 *
		 */
		return response;
	}

	@Override
	public Coupon getCouponById(Long id) {
		// TODO Auto-generated method stub
		Optional<Coupon> coupon = couponRepository.findById(id);
		return coupon.isPresent() ? coupon.get() : null;
	}

	@Override
	public Map<String, String> deleteCoupon(Long id) {
		Map<String, String> response = new HashMap<>();
		try {
			Optional<Coupon> coupon = couponRepository.findById(id);
			if (coupon.isPresent()) {

				couponRepository.deleteById(id);
				response.put(AppConstants.DELETE, AppConstants.SUCCESS);
			} else {
				response.put(AppConstants.FAIL, AppConstants.INVALID_COUPON);
			}

		} catch (Exception e) {
			// TODO: handle exception
			response.put(AppConstants.FAIL, e.getMessage());
		}
		return response;
	}

	@Override
//	public List<CouponDto> getApplicableCoupons(Cart cart) {
//		/*
//		 * sort coupons based on highest discount
//		 */
//		Integer cartValue = 0;
//		List<Long> productList = new ArrayList<>();
//		List<String> bogo = new ArrayList<>();
//		for (ProductDto product : cart.getProducts()) {
//			cartValue += product.getPrice();
//			productList.add(product.getId());
//			bogo.add(product.getId() + "" + product.getQuantity());
//
//		}
//		List<Coupon> applicableCoupons = couponRepository.getApplicableCoupons(cartValue, productList);
//		return null;
//	}

	public List<CouponDto> getApplicableCoupons(Cart cart) {
		/*
		 * sort coupons based on highest discount
		 */
		Gson json = new Gson();
		Double cartValue = 0.0;
		Set<Long> productList = new HashSet<>();
		Map<Long, Integer> productMap = new HashMap<>();
		List<CouponDto> response = new ArrayList<>();

		for (ProductDto product : cart.getProducts()) {
			cartValue += (product.getPrice()*product.getQuantity());
			productMap.put(product.getId(), product.getQuantity());

		}
		List<Coupon> couponList = couponRepository.findAll();
		for (Coupon coupon : couponList) {
			Double discount = 0.0;
			logger.info(coupon.getCondition());
			String s=coupon.getCondition();
			logger.info("");
			logger.info(coupon.getCondition().toString());
			ObjectMapper om = new ObjectMapper();
			Condition condition =null;
			try {
			condition = om.readValue(s, Condition.class);
			}catch (Exception e) {
				logger.error("error ");
			}
//			Condition condition = json.fromJson(coupon.getCondition().toString(), Condition.class);
			boolean isEligible = false;
			CouponDto dto = new CouponDto();
			dto.setId(coupon.getCouponId());
			if (condition.getBxgy() != null) {
				List<ProductDto> buyList = condition.getBxgy().get("buy");
				List<ProductDto> getList = condition.getBxgy().get("get");
				boolean canApply = true;
				int noOfFree = 0;
				int minFree = 0;
				for (ProductDto mandate : buyList) {
					if (productMap.containsKey(mandate.getId())) {
						if (!(productMap.get(mandate.getId()) >= mandate.getQuantity())) {
							noOfFree = productMap.get(mandate.getId()) / mandate.getQuantity();
							minFree = minFree == 0 ? noOfFree : Math.min(noOfFree, minFree);

						} else {
							canApply = false;
						}
					} else {
						canApply = false;
						break;
					}
					if (canApply) {
						discount = couponUtil.addQuantity(getList, noOfFree);
						cart.getProducts().addAll(getList);
						isEligible = true;
						dto.setType("BOGO");
						dto.setDiscount(discount);
					}
				}
			}
			if (condition.getMinVal() != null) {
				if (condition.getMinVal() < cartValue) {
					isEligible = true;
					logger.info("min value coupon can be applied");
					if (condition.getDiscountType().equals(AppConstants.DISCOUNT_TYPE_AMOUNT)) {
						logger.info("discoun type is amount");
						discount += condition.getDiscount();
						dto.setType("CARTWISE");
						dto.setDiscount(discount);
					} else {
						logger.info("discoun type is percent");
						discount = (cartValue * condition.getDiscount()) / 100;
						if (discount > condition.getDiscMax()) {
							discount = condition.getDiscMax().doubleValue();
						}
						dto.setType("CARTWISE");
						dto.setDiscount(discount);
					}
				}
			}
			if (condition.getProductWise() != null) {
				ProductDto product = condition.getProductWise();
				if (productMap.containsKey(product.getId())) {
					isEligible = true;
					if (product.getDiscType().equals(AppConstants.DISCOUNT_TYPE_AMOUNT)) {
						logger.info("discoun type is amount");
						discount += product.getDiscount();
						dto.setType("PRODUCTWISE");
						dto.setDiscount(discount);
					} else {
						logger.info("discoun type is percent");
						discount = (cartValue * product.getDiscount()) / 100;
						if (discount > product.getDiscMax()) {
							discount = condition.getDiscMax().doubleValue();
						}
						dto.setType("PRODUCTWISE");
						dto.setDiscount(discount);
					}
				}
			}
			if (isEligible) {
				response.add(dto);
			}
		}

		return response;
	}

}
