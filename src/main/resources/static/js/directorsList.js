const rowsPerPage = 5;
let currentPage = 1;
let allRows = [];
let filteredRows = [];

function paginateTable(rows) {
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    document.getElementById('page-info').textContent = `Page ${currentPage} of ${totalPages}`;

    allRows.forEach(row => row.style.display = 'none'); 

    rows.forEach((row, index) => {
        row.style.display = (index >= (currentPage - 1) * rowsPerPage && index < currentPage * rowsPerPage) ? '' : 'none';
    });

    document.getElementById('prev-btn').disabled = currentPage === 1;
    document.getElementById('next-btn').disabled = currentPage === totalPages || totalPages === 0;
}

function changePage(offset) {
    currentPage += offset;
    paginateTable(filteredRows);
}

function toggleFormVisibility() {
    const form = document.getElementById('director-form');
    const showFormBtn = document.getElementById('show-form-btn');
    if (form.style.display === 'none') {
        form.style.display = 'block';
        showFormBtn.style.display = 'none';
    } else {
        form.style.display = 'none';
        showFormBtn.style.display = 'block';
    }
}

function filterRows(query) {
    return allRows.filter(row => {
        const name = row.querySelector('td:nth-child(2) a').textContent.toLowerCase();
        return name.startsWith(query);
    });
}

function filterTable() {
    const searchInput = document.getElementById('search-input').value.toLowerCase();
    currentPage = 1;
    filteredRows = filterRows(searchInput);
    paginateTable(filteredRows);
}

document.addEventListener('DOMContentLoaded', () => {
    allRows = Array.from(document.querySelectorAll('#directors-tbody tr'));
    filteredRows = allRows; 
    paginateTable(allRows);
    document.getElementById('search-input').addEventListener('input', filterTable);
});
