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
		} catch (Exception e) {
			// TODO: handle exception
			response.put(AppConstants.FAIL, e.getMessage());
		}
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
			if(coupon.isPresent()) {
				
			couponRepository.deleteById(id);
			response.put(AppConstants.DELETE, AppConstants.SUCCESS);
			}else {
				response.put(AppConstants.FAIL, AppConstants.INVALID_COUPON);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			response.put(AppConstants.FAIL, e.getMessage());
		}
		return response;
	}

	@Override
	public List<CouponDto> getApplicableCoupons(Cart cart) {
		/*
		 * sort coupons based on highest discount
		 */
		Integer cartValue=0;
		List<Long> productList=new ArrayList<>();
		List<String>bogo=new ArrayList<>();
		for(ProductDto product:cart.getProducts()) {
			cartValue+=product.getPrice();
			productList.add(product.getId());
			bogo.add(product.getId()+""+product.getQuantity());
			
		}
		List<Coupon>applicableCoupons=couponRepository.getApplicableCoupons(cartValue,productList);
		return null;
	}
	public List<CouponDto> getApplicableCoupon(Cart cart) {
		/*
		 * sort coupons based on highest discount
		 */
		Gson json=new Gson();
		Double cartValue=0.0;
		Set<Long> productList=new HashSet<>();
		Map<Long,Integer>productMap=new HashMap<>();
		List<CouponDto> response=new ArrayList<>();
		for(ProductDto product:cart.getProducts()) {
			cartValue+=product.getPrice();
			productMap.put(product.getId(),product.getQuantity());
			
		}
		List<Coupon>couponList=couponRepository.findAll();
		for(Coupon coupon:couponList) {
			Double discount=0.0;
			Condition condition= json.fromJson(coupon.getCondition(), Condition.class);
			if(condition.getBxgy()!=null) {
				List<ProductDto> buyList=condition.getBxgy().get("buy");
				List<ProductDto> getList=condition.getBxgy().get("get");
				boolean canApply=true;
				for(ProductDto product:buyList) {
					if(productMap.containsKey(product.getId())) {
						if(!(productMap.get(product.getId())>=product.getQuantity())) {
							int noOfFree=productMap.get(product.getId())/product.getQuantity();
							discount=couponUtil.addQuantity(getList, noOfFree);						
							cart.getProducts().addAll(getList);
							
						}
					}else {
						break;
					}
				}
			}
			if(condition.getMinVal()!=null) {
				if(condition.getMinVal()>cartValue) {
					logger.info("min value coupon can be applied");
					if(condition.getDiscountType().equals(AppConstants.DISCOUNT_TYPE_AMOUNT)) {
						logger.info("discoun type is amount");
						discount+=condition.getDiscount();
					}else {
						logger.info("discoun type is percent");
						discount=(cartValue*condition.getDiscount())/100;
						if(discount>condition.getDiscMax()) {
							discount=condition.getDiscMax().doubleValue();
						}
					}
				}
			}if(condition.getProductWise()!=null) {
				ProductDto product=condition.getProductWise();
				if(productMap.containsKey(product.getId())) {
					if(product.getDiscType().equals(AppConstants.DISCOUNT_TYPE_AMOUNT)) {
						logger.info("discoun type is amount");
						discount+=product.getDiscount();
					}else {
						logger.info("discoun type is percent");
						discount=(cartValue*product.getDiscount())/100;
						if(discount>product.getDiscMax()) {
							discount=condition.getDiscMax().doubleValue();
						}
					}
				}
			}
		}
		
		return null;
	}

}
