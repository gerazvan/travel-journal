# Travel Journal

Main features:  
• all the data from the app is saved in a Firebase Cloud Firestore  
• the favourite trips are also saved locally in an SQLite database by using Room  

![Cloud Firestore](https://lh3.googleusercontent.com/jNDH6uzojgehnMD6XU4p2HqSG2Fx7WaYt9xnEVewk9GOI8gUay2jzuX1NRkH7tZJIiYgO2Dh6WWkvrTjbJ9YEXGWUVTMqI9wdaXD0f1iJwFG7mbfOSBRuV5p4869b2wUEt5rFbHrDKskxjKFoYZnS4se2Gi6fIklU48jYm1_XELk-25QWigVIVe4lFuRpWCqol47ohKX67fY2BPCKlpy7WhbXSOPuwWfcJRuKeYR8iECRfr1yFnVIgWUoG3IxeiB43WCUyegkwIbEm0CvDEZPmRmLyYuq8Ouf1JVNHJ38xMiX-7cd-dcuI-kKe2IxlPe-gbOWAuF4e_w4Zz2nrYW2K9G6sV6ilW_CWQX26Rd-XsPqsMTeLgpC2Eum1Zv5fste0mu1ZQ1YCj1EGvTiRZ4MnyVUiEiCzC9VnyVrfwXusVkyUpyaUHG4G5Ft1ZcxBr1TbLeSO1iY6GbTlWED-7Lwqtk9ZvGH32ED0lx9NtUl0T9F7xjZjtOMDffCp7os4eGBNwXf_TP_zEYvPZnTgZT7_rN-PRER70jKlvpY2hI2CM333e5JXWvqoZD3CIk46mCqm0Yp6nDG4nv7tQ3leriz1bziOJFiB5BUDaXk8Vs1waUvIdAa6OyjOM4SF6nmqpfeMVqVtgMb2fsSbLxRD5uCyKVALAgiSU=w1530-h735-no)

Activities:  
  
 • Sign In  
  -> upon opening the app, if the user is not already signed in, the main activity will send you to the Sign In activity  
  -> implements authentication using the Google account with Firebase Authentication  
    
• Navigation Drawer (main activity)  
  -> the header displays the profile pic, the e-mail address and the name of the Google account used to sign in.  
  -> the body contains the following implemented items  
    - Home - leads to the home screen of the app  
           - it's a RecyclerView that displays the list of trips  
           - each RecyclerView item contains a small bookmark image that lets you add the trip to favourites  
    - Favourite - the same home screen, but the query of the RecyclerView's adapter is changed to only display the trips marked as favourite  
    - About us  
    - Contact us  
    - Sign out - signs out of the Google account  
    - Share  
    - Send  
      
• Add or Edit Trip  
  -> the home screen contains a Floating Action Button that leads to this activity  
  -> each RecyclerView item can be edited when long clicked  
  -> contains the following fields:  
    - Trip Name (EditText input)  
    - Destination (EditText input)  
    - Trip Type (radio group with 3 choices: City Break, Seaside, Mountains)  
    - Price (slider)  
    - Start Date (custom date picker)  
    - End Date (custom date picker)  
    - Rating (rating bar)  
    - An Image (either selected from the gallery, or taken using a camera intent)  
  -> Once clicked, the save button sends all the data back to the main activity, which will either add or edit the trip in the Cloud Firestore  
   
 • View Trip  
  -> when clicked, each RecyclerView item displays the detalis of the trip in read only mode  
  
    
