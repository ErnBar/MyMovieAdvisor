function searchFilm(){
    var title = document.getElementById("search-film");
    window.location.href = "/movieDetail?title=" + title.value;
    
}