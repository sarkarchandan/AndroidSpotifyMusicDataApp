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

## Build and Deploy
#### This project has been created with Android Studio version 2.2.3 and cradle has been used as the default build system. Application packages will be installed to the device or emulator with the help of Android Debug Bridge tools. 
```
git clone git@github.com:sarkarchandan/AndroidSpotifyMusicDataApp.git - To clone the project from the GitHub repository.
gradle clean - To clean the project and the previous build files if any.
gradle build - To build the current project and prepare the apk file.
```
## Sample Execution
#### Following are some snapshots taken from the application.
##### 1. Handling Authentication with Spotify native client.
##### 2. Sample New Release main activity screen with CardView.
##### 3. Sample new Release main activity screen with CardView.
##### 4. Sample Album activity screen tapped from the cardview inflated with underlying tracks of the album.
##### 5. Sample Album activity screen tapped from the cardview inflated with underlying tracks of the album.
##### 6. Sample Artist  activity screen tapped on the flat button inflated with albums of the selected artist.
##### 7. Sample Artist  activity screen tapped on the flat button inflated with albums of the selected artist.
##### 8. Sample Artist  activity screen tapped on the flat button inflated with albums of the selected artist.
##### 9. Sample inflated Album randomly chosen and tapped from the list of albums of a specific artist.
##### 10. Sample inflated Album randomly chosen and tapped from the list of albums of a specific artist.
<img src="https://cloud.githubusercontent.com/assets/19269229/21650600/c5fe52ba-d2a5-11e6-87b7-5d73081d5d93.png" alt="Authentication with native Spotify clicent" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21650969/102dc054-d2a7-11e6-89f0-7a0b4ebcb163.png" alt="New releases cardview" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651104/af2c7592-d2a7-11e6-9dbe-7245c48cfbe3.png" alt="New released album cardview" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651183/f59f9dd8-d2a7-11e6-9202-747524d039e7.png" alt="new released album inflated" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651225/206bd40a-d2a8-11e6-9e12-3306c14c370f.png" alt="new released album inflated" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651261/3c048252-d2a8-11e6-9571-1c93702c9cf3.png" alt="artist inflated" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651285/5a5bbbc6-d2a8-11e6-8cf4-87abddc641b9.png" alt="artist inflated" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651419/d22fcf3e-d2a8-11e6-9e0a-f6a5ee2ffa03.png" alt="artist inflated" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651484/10cddda8-d2a9-11e6-94db-24cf39061c04.png" alt="artist album inflated" width="150" height="300"> <img src="https://cloud.githubusercontent.com/assets/19269229/21651513/2b61a3fc-d2a9-11e6-9aa2-69dc6792aeba.png" alt="artist album inflated" width="150" height="300"> 

## Known Issues
##### Currently the application is performing a lot of tasks in the background thread before making the data available to the UI which does not lead the application to crash but occasionally causes few seconds of unresponsive state. Work is being done to improve on application performance and mitigate such issues and at same time to improve on the UI by adhering to the material design guidelines. I am open to any possible suggestion that helps me to learn.
