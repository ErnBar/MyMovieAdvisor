function searchDirectors() {
    const input = document.getElementById('directorNameInput').value;
    const dropdown = document.getElementById('directorList');
    
    if (input.length > 0) {
        fetch(`/searchDirectors?prefix=${input}`)
            .then(response => response.json())
            .then(data => {
                dropdown.style.display = 'block';
                dropdown.innerHTML = '';
                data.forEach(director => {
                    const option = document.createElement('option');
                    option.value = director.name;
                    option.text = director.name;
                    dropdown.appendChild(option);
                });
            });
    } else {
        dropdown.style.display = 'none';
        dropdown.innerHTML = '';
    }
    
    dropdown.onclick = function() {
        document.getElementById('directorNameInput').value = dropdown.value;
        dropdown.style.display = 'none';
    }
}

function searchActors() {
    const input = document.getElementById('actorNameInput').value;
    const dropdown = document.getElementById('actorList');
    
    if (input.length > 0) {
        fetch(`/searchActors?prefix=${input}`)
            .then(response => response.json())
            .then(data => {
                dropdown.style.display = 'block';
                dropdown.innerHTML = '';
                data.forEach(actor => {
                    const option = document.createElement('option');
                    option.value = actor.name;
                    option.text = actor.name;
                    dropdown.appendChild(option);
                });
            });
    } else {
        dropdown.style.display = 'none';
        dropdown.innerHTML = '';
    }
    
    dropdown.onclick = function() {
        document.getElementById('actorNameInput').value = dropdown.value;
        dropdown.style.display = 'none';
    }
}

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

