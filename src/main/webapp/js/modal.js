$(document).ready(function() {
    // $('<script/>',{type:'text/javascript', src:'js/modal.js'}).appendTo('head');
    var modal = document.getElementById("myModal");
 //   alert(modal);
    // Get the button that opens the modal
 //   var btn = document.getElementById("myBtn");
 //   alert(btn);
    // Get the <span> element that closes the modal
    var span = document.getElementsByClassName("close")[0];

    // When the user clicks on <span> (x), close the modal
    span.onclick = function() {
        modal.style.display = "none";
    }
    
    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
});

