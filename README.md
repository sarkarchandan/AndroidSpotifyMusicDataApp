## AndroidSpotifyMusicDataApp
### [Spotify](https://www.spotify.com/de/) is a Swedish music, podcast, and video streaming service which exposes considerable amount of the music related data for the developers to use in their applications for free. In this application, the [android wrapper for the Spotify Web Api](https://github.com/kaaes/spotify-web-api-android) has been used to share the music related information in concise manner. It majorly focuses on new album releases offered by Spotify. 

#### Application exposes newly released Spotify albums using Android CardView library. Each Card in the UI displays one recently released Spotify album along with the associated artist name. It also offers a flat-button which offers additional information on the associated artist.

#### EventListener OnClickListener has been used both on the cards and naturally on the button to respond the respective touch event.

#### In response to the touch event on the card, associated album image will be loaded and the viewer will be offered with additional relevant information on the specific album such as album release dates and the available tracks in the album, added with their respective track-durations and the popularity converted to rating. This list tracks are displayed will the help of android RecyclerView library.

#### In response to the about artist flat button, associated artist image will be loaded along with the artist genres and popularity of the artist converted to rating. In addition to that, all registered album details of the specific artist will be loaded added with their respective album release dates and album popularity converted to rating. This view has also been achieved with the android RecyclerView library. EventListener OnClickListener has been added to each album item which enables a touch event on each of the album items as well. Upon tapping on each of the album items, respective album will be inflated to reveal the underlying detail of the album invoking a similar effect as with the touch event on the cards.
#### A total of three activities has been used to create this functionality and one activity has been reused. All requests apart of the authentication has been handled on he background thread using AsyncTask.
 

## Authentication
#### [Spotify Android SDK Authentication Guide](https://developer.spotify.com/technologies/spotify-android-sdk/android-sdk-authentication-guide/) has been followed to handle the authentication and generate the access token. We have registered with Spotify prior to the design of the application. Spotify Android-SDK supports two ways to deal with the authentication such as single-sign-on with the Spotify client and fallback strategy with the help of default android WebView. In our application we have used the single-sign-on functionality with the help of pre-installed Spotify client. In the starting of the application it sends an authentication request against the registered Spotify account and generate the access token. This access token is used to fetch the subsequent data from the Spotify Web API in all subsequent calls.

### Ongoing Work
- Further optimise the application for better performance and efficiency.
- Adhere to the Material Design guidelines to improve the user interface.
- Enable music streaming on the application.
- Enable standalone look up functionality for artists, albums and tracks.
- Find more dynamic solution for handling the authentication compared to what currently being used.
- Add Dynamic Programming startegy and reduce network operations. it will add to the efficeincy.
- Apply Responsive Design construct to ensure that the app can adapt to different screen size and devices.
- Review the layouts and views used in the app and consider if more simpler approach could be used.

## Build and Deploy
#### This project has been created with Android Studio version 2.2.3 and gradle has been used as the default build system. Application packages will be installed to the device or emulator with the help of Android Debug Bridge tools. 
```
git clone git@github.com:sarkarchandan/AndroidSpotifyMusicDataApp.git - To clone the project from the GitHub repository.
gradle clean - To clean the project and the previous build files if any.
gradle build - To build the current project and prepare the apk file.
```
## Sample Execution
#### Following are some snapshots taken from the application.
##### 1. Handling Authentication with default web view.
##### 2. Sample New Release main activity screen with CardView.
##### 3. Sample Album activity screen on tapping the card from main activity.
##### 4. Sample Album activity screen on tapping the album of a given artist from artist activity.
##### 5. Sample artist activity screen on button click from the cardview shoing all registered albums of that artist.
##### 6. Sample artist activity screen on button click from the cardview shoing all registered albums of that artist.
<img src="https://cloud.githubusercontent.com/assets/19269229/21650600/c5fe52ba-d2a5-11e6-87b7-5d73081d5d93.png" alt="Authentication with native Spotify clicent" width="150" height="300"> 
<img src="https://cloud.githubusercontent.com/assets/19269229/22215707/a83db6ac-e19c-11e6-8665-d030fcb7a377.png" alt="New releases cardview" width="150" height="300">
<img src="https://cloud.githubusercontent.com/assets/19269229/22215874/359d7d98-e19d-11e6-8004-81d8dd1e18cf.png" alt="Album page on card tap" width="150" height="300">  
<img src="https://cloud.githubusercontent.com/assets/19269229/22215971/8484aa12-e19d-11e6-97d7-dcc538763410.png" alt="Album page on card tap from artist" width="150" height="300"> 
<img src="https://cloud.githubusercontent.com/assets/19269229/22216021/ab74a744-e19d-11e6-9e87-ec998f879323.png" alt="Artist details on flat button tap" width="150" height="300"> 
<img src="https://cloud.githubusercontent.com/assets/19269229/22216064/d5dd5d14-e19d-11e6-9286-d75e568743b8.png" alt="Artist details on flat button tap" width="150" height="300"> 
## Known Issues
##### Currently the application is performing a lot of tasks in the background thread before making the data available to the UI which does not lead the application to crash but occasionally causes few seconds of unresponsive state. Work is being done to improve on application performance and mitigate such issues and at same time to improve on the UI by adhering to the material design guidelines. I am open to any possible suggestion that helps me to learn.
