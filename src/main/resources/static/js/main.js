function searchFilm() {
    var title = document.getElementById("search-film").value;
    if (title.length > 0) {
        fetch(`/searchMovies?prefix=${title}`)
            .then(response => response.json())
            .then(data => {
                var results = document.getElementById("search-results");
                results.innerHTML = ''; // Clear previous results
                if (data.length > 0) {
                    data.forEach(movie => {
                        var li = document.createElement("li");
                        li.innerText = movie.title;
                        li.onclick = function() {
                            window.location.href = `/movieDetail?title=${movie.title}`;
                        };
                        results.appendChild(li);
                    });
                } else {
                    results.innerHTML = '<li>No results found</li>';
                }
            });
    } else {
        document.getElementById("search-results").innerHTML = '';
    }
}

document.addEventListener('DOMContentLoaded', (event) => {
    const randomTitle = document.getElementById('random').textContent;
    const button = document.getElementById('random-button');

    button.addEventListener('click', () => {
        if (randomTitle) {
            window.location.href = `/movieDetail?title=${randomTitle}`;
        } else {
            alert('No random title available');
        }
    });
});