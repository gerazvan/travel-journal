# Travel Journal

Main features:  
• all the data from the app is saved in a Firebase Cloud Firestore  
• the favourite trips are also saved locally in an SQLite database by using Room  

![Cloud Firestore](https://lh3.googleusercontent.com/eRwlHtZW-jB7zj9ixyhQaCKYGjFVsqENYC4sGFgMdLNM3pXvePfEsG8wN3n1Dwjc8Dq6g77Y-J56mxnvS-DPOJeuFs2_ZZeJuXDaELy_AeN2FtKXp5Yue8GBKVYFoL1LtupwOlu1zNQ_y3ovLvUd2fwKNsWCuIvWbKJ4WC5CwhbKNX45B5-6c6Ao-Dlu58plwHiL7EtixxzZDJ4hXwoMxkgSrkRTvYH5RJHhSHkKdX65LpsGWdVT_i_9yg9hb_zjqkvRHL9isiJjf8zoYYfM63xMcT7bRK7LvjpRL6y6rrPc0eXk3_g5JD3-1vKuwH1CHnRKX7SPayMMpLu2GY2bLECb262EHCP8cvc1M3ecqWtRoePY02zt7k69PSU37ck4eWMoOEfgbLcYZtA_9W1brJqA6F_sTZyI2But2gucLc149oBKp2PS_0hyuyz-I4o20qwfd-4ClKqvXBmto07k5tEhENi-9y2M5nTsbq_Icth7r7x_ZqI6OsA77lCkkfhn9DGn9SNbv8mX8OqF_uG5D1Upqyv1EK_jAXXYV45yDCjZ6JclMKdvYBdqlmzfmzjo-q5wPtmDYw6S7gVJJ9gL495_unzdJ0u4NBp1Bxu343U-RNbrCI9YG38mSDF4VgkeJ0Sn2ipHcmR6uxrX_8j-3kiNTWddwmY=w1530-h735-no)

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
  
    
