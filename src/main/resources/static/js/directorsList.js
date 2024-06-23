const rowsPerPage = 5;
let currentPage = 1;

function paginateTable() {
    const rows = document.querySelectorAll('#directors-tbody tr');
    const totalPages = Math.ceil(rows.length / rowsPerPage);
    document.getElementById('page-info').textContent = `Page ${currentPage} of ${totalPages}`;

    rows.forEach((row, index) => {
        row.style.display = (index >= (currentPage - 1) * rowsPerPage && index < currentPage * rowsPerPage) ? '' : 'none';
    });

    document.getElementById('prev-btn').disabled = currentPage === 1;
    document.getElementById('next-btn').disabled = currentPage === totalPages;
}

function changePage(offset) {
    currentPage += offset;
    paginateTable();
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

document.addEventListener('DOMContentLoaded', () => {
    paginateTable();
});
