# Travel Journal

Main features:  
&nbsp;&nbsp;&nbsp;• all the data from the app is saved in a Firebase Cloud Firestore  
&nbsp;&nbsp;&nbsp;• the favourite trips are also saved locally in an SQLite database by using Room  

![Cloud Firestore](https://lh3.googleusercontent.com/jNDH6uzojgehnMD6XU4p2HqSG2Fx7WaYt9xnEVewk9GOI8gUay2jzuX1NRkH7tZJIiYgO2Dh6WWkvrTjbJ9YEXGWUVTMqI9wdaXD0f1iJwFG7mbfOSBRuV5p4869b2wUEt5rFbHrDKskxjKFoYZnS4se2Gi6fIklU48jYm1_XELk-25QWigVIVe4lFuRpWCqol47ohKX67fY2BPCKlpy7WhbXSOPuwWfcJRuKeYR8iECRfr1yFnVIgWUoG3IxeiB43WCUyegkwIbEm0CvDEZPmRmLyYuq8Ouf1JVNHJ38xMiX-7cd-dcuI-kKe2IxlPe-gbOWAuF4e_w4Zz2nrYW2K9G6sV6ilW_CWQX26Rd-XsPqsMTeLgpC2Eum1Zv5fste0mu1ZQ1YCj1EGvTiRZ4MnyVUiEiCzC9VnyVrfwXusVkyUpyaUHG4G5Ft1ZcxBr1TbLeSO1iY6GbTlWED-7Lwqtk9ZvGH32ED0lx9NtUl0T9F7xjZjtOMDffCp7os4eGBNwXf_TP_zEYvPZnTgZT7_rN-PRER70jKlvpY2hI2CM333e5JXWvqoZD3CIk46mCqm0Yp6nDG4nv7tQ3leriz1bziOJFiB5BUDaXk8Vs1waUvIdAa6OyjOM4SF6nmqpfeMVqVtgMb2fsSbLxRD5uCyKVALAgiSU=w1530-h735-no)

Activities:  
  
 • Sign In  
 &nbsp;&nbsp;&nbsp;-> upon opening the app, if the user is not already signed in, the main activity will send you to the Sign In activity  
 &nbsp;&nbsp;&nbsp;-> implements authentication using the Google account with Firebase Authentication  
    
• Navigation Drawer (main activity)  
&nbsp;&nbsp;&nbsp;-> the header displays the profile pic, the e-mail address and the name of the Google account used to sign in.  
&nbsp;&nbsp;&nbsp;-> the body contains the following implemented items  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Home - leads to the home screen of the app  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- it's a RecyclerView that displays the list of trips  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- each RecyclerView item contains a small bookmark image that lets you add the trip to favourites  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Favourite - the same home screen, but the query of the RecyclerView's adapter is changed to only display the trips marked as favourite  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- About us  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Contact us  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Sign out - signs out of the Google account  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Share  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Send  
      
• Add or Edit Trip  
&nbsp;&nbsp;&nbsp;-> the home screen contains a Floating Action Button that leads to this activity  
&nbsp;&nbsp;&nbsp;-> each RecyclerView item can be edited when long clicked  
&nbsp;&nbsp;&nbsp;-> contains the following fields:  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Trip Name (EditText input)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Destination (EditText input)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Trip Type (radio group with 3 choices: City Break, Seaside, Mountains)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Price (slider)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Start Date (custom date picker)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- End Date (custom date picker)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rating (rating bar)  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- An Image (either selected from the gallery, or taken using a camera intent)  
&nbsp;&nbsp;&nbsp;-> Once clicked, the save button sends all the data back to the main activity, which will either add or edit the trip in the Cloud Firestore  
   
 • View Trip  
&nbsp;&nbsp;&nbsp;-> when clicked, each RecyclerView item displays the detalis of the trip in read only mode  
  
    
