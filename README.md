# CouponManager
These Apis are used for creating , altering ,viewing and applying coupons.

Core Requirements:-(Implemented)
Coupon Types:-
Cart-Wise Coupons: discount applied to total cart value if certain condition is met.
  -Coupon can have discount value in percentage or amount, 
  -discount is applied to the cart only if min value if x.
  
Product-Wise Coupons: discount applied to specific products in the cart based on productId,category, brand.
   -if product id x is present in cart discount get applied which can be in percentage or amount.
 
Buy X Get Y(BxGY)Coupons: Offers include if you buy 2 products you get 1 free
  -buy n quantity of x and get m quantity of  y free
  -buy n quantity of x ,buy m quantity of  y and get o quantity of z free
  -check if the free product is available in the mart.
  -add the free products to cart and then return the cart.


Generic Cases
-apply coupons only if they are not expired
-if discount is in percentage , we can set the maximum discount.
-check if the maximum number of coupons value is not 0 (To be implemented)
-check if the user is first time user  (To be implemented)
