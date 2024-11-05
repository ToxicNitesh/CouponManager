# CouponManager
These Apis are used for creating , altering ,viewing and applying coupons.

Core Requirements:-(Implemented)
Coupon Types:-
Cart-Wise Coupons: discount applied to total cart value if certain condition is met.
  -Coupon can have discount value in percentage or amount, 
  -discount is applied to the cart only if min value if x.
  
Product-Wise Coupons: discount applied to specific products in the cart based on productId,category, brand.
   -if product id x is present in cart discount get applied which can be in percentage or amount.
   -product based coupons can only be applied if min number of products in y
   -get discount on particular brand
   -get discount for particular catergory
   -get productWise discount only for non first time user
   
Buy X Get Y(BxGY)Coupons: Offers include if you buy 2 products you get 1 free
  -buy n quantity of x and get m quantity of  y free
  -buy n quantity of x ,buy m quantity of  y and get o quantity of z free
  -check if the free product is available in the mart.
  -add the free products to cart and then return the cart.
  -only apply coupon is there are only x products in cart.
  -wont get this offer if you have x product in your cart.
  -bxgy will be eligible if the cart value is min 500

Written a batch job to deactive the coupons which are left 0 or it is expired.
Generic Cases
-apply coupons only if they are not expired
-if discount is in percentage , we can set the maximum discount.
-check if the maximum number of coupons value is not 0 (To be implemented)
-check if the user is first time user and give x discount (To be implemented)
-have a maximum no of coupons available for per user and overall users
-what should be priority if multiple coupons get applied (combination of diferent types eg CARTWISE+productwise)

We can also have the flexibilty to combine coupons .We can make a coupon which gives discount if having  cart value > x or/and if the cart is having the specific product i.e CARTWISE + PRODUCTWISE.

While creating coupons we can also add constriant of giving previlage of creating a combinational coupon to specific sellers.

Edge Cases while creating a coupon:-
coupon type should not be null;
if coupon type is x then the data related to x only should be filled.other should be null
coupon should always have a from and to date



edge cases while applying the coupon.
if no specific couponid passed then coupon with max disocunt should be auto applied.


limitations:-
I have given the option to add any type of coupon , but while applying the coupon we need a code change
performace might be slow as no caching mechanism is applied.
We also need to have a lock on database if one operation is going on so there exists no case wherein user have consumed the coupon but coupon left was 0

