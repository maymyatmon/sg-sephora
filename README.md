# sg-sephora

#Data Source
https://api.sephora.sg/api/v2.5/products?number=1&size=30&landing_page=sale&include=brand,option_types.option_values,featured_variant,featured_ad&sort=sales

#Remarks
- Rating values in API response are same for all products for page 1. 
- Provided API seems to retrieve sale items but the response do not include any sale items.

#Implementation 
- Used Retrofit to communicate with API.
- Created model classes (DataReponse,Product,Included) to map API response. 
- Created View Model class (MainViewModel) to prepare data for fragments.
- First Fragment > Showing the products as 2 colums grid layout in recycler view with GridLayoutManager. Each item displays the product’s brand name, product name, original price, product rating, and number of shades/variants.
- Second Fragment > When a user clicks an item on listing page, a product details page is opened displaying photo carousel, brand name, product name, product rating, original price, discount price and product description.

#ScreenRecording
https://drive.google.com/file/d/1hpT80KJPpm4iOdkZJo5rp6Ux-Zshv-Bu/view?usp=sharing
