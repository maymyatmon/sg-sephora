# sg-sephora

#Data Source
https://api.sephora.sg/api/v2.5/products?number=1&size=30&landing_page=sale&include=brand,option_types.option_values,featured_variant,featured_ad&sort=sales

#NOTE
- Rating data in API response is same for all products for page 1. 
- Provided API seems to retrieve sale items but the response do not include any sale items.

#Implementation 
- Used Retrofit to communicated with API.
- Showing the products in recycler view with GridLayoutManager


