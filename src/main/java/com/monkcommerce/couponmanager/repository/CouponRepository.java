package com.monkcommerce.couponmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.monkcommerce.couponmanager.dto.ProductDto;
import com.monkcommerce.couponmanager.entity.Coupon;
import com.monkcommerce.couponmanager.util.CouponSqlQueries;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

//	@Query(value=CouponSqlQueries.GET_APPLICABLE_COUPONS,nativeQuery=true)
//	List<Coupon> getApplicableCoupons(Integer cartValue, List<Long> productList);
//			, List<ProductDto> bogo);

}
