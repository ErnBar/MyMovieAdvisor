const rowsPerPage = 5;
let currentPage = 1;

function paginateTable() {
    const rows = document.querySelectorAll('#users-tbody tr');
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

document.addEventListener('DOMContentLoaded', () => {
    paginateTable();
});
