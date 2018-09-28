/*
  Interact with reddit to get videos
*/

// The list of videos with information about them
var videosList = [];

// The index in videosList that is currently rendered
var currentIndex = 1;

var Reddit = {
  getVideos : function(subreddit, category, callback) {
    // Get the list of videos from the Reddit api through a custom http proxy
    // to get around browser cross origin policies
    $.getJSON(
      "https://ha5u8zbu2k.execute-api.us-west-2.amazonaws.com/prod/redditproxy/" + subreddit + "/" + category,
      function(data) {
        videosList = videosList.concat(data.data.children);
        if(callback != null) {
          callback(videosList);
        }
      }
    );
  },
  renderVideos: function() {
    // Remove all previous videos
    $(".video").remove();

    // Render the current index in the videosList
    var urlString = videosList[currentIndex].data.url;

    // Get the videoID
    if(urlString.includes("youtube.com")) {
      var videoID = urlString.split("?v=")[1];
    } else if(urlString.includes("youtu.be")) {
      var videoID = urlString.split(".be/")[1];
    } else {
      console.log("Error: was not a youtube url. Skipping to the next video.");
      currentIndex++;
      renderVideos();
      return;
    }

    // Use the custom service to get around cross origin policies
    var url = "https://ha5u8zbu2k.execute-api.us-west-2.amazonaws.com/prod/youtubeproxy/" + videoID;

    // Create the iframe
    $('<iframe>', {
       src: url,
       frameborder: 0,
       height: "571",
       width: "945",
       class: "video"
     }).appendTo('#videos');

     // Add the title
     $("#videos").append("<h3 id='video-title' class='video'>" + videosList[currentIndex].data.title + "</h1>");

     // Add the user who submitted
     $("#video-title").append("<br><small>Submitted by " + videosList[currentIndex].data.author + "</small>");
  },
  next: function() {
    if(currentIndex < videosList.length) {
      currentIndex++;
      renderVideos();
    } else {
      console.log("Getting more videos");

    }
  },
  prev: function() {
    if(currentIndex > 1) {
      currentIndex--;
      renderVideos();
    } else {
      console.log("At the end of the list already");
    }
  }
}

var renderVideos = Reddit.renderVideos;
