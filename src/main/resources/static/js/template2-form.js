(() => {
    document.addEventListener('DOMContentLoaded', function () {
        let mainUrl = "http://localhost:8081/api/template2";
        let isLoading = false;
        let currentPage = 0;
        let pageSize = 5;

        function fetchSuggestions(query = '', target, page = 0) {
            if (isLoading) return;
            isLoading = true;

            const suggestionsContainer = document.querySelector(`.suggestions.kodeTemplate1`);
            suggestionsContainer.innerHTML = '<div class="loading-spinner">Memuat...</div>';

            fetch(`${mainUrl}/kodeTemplate1-options?page=${page}&size=${pageSize}`)
                .then(response => response.json())
                .then(data => {
                    isLoading = false;
                    suggestionsContainer.innerHTML = '';
                    currentPage = data.page;

                    let filteredData = query
                        ? data.content.filter(item => item.nama.toLowerCase().includes(query.toLowerCase()))
                        : data.content;

                    if (filteredData.length > 0) {
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = `${item.kode} - ${item.nama}`;

                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#kodeTemplate1`);
                                inputField.value = `${item.kode} - ${item.nama}`;
                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });

                        const paginationContainer = document.createElement('div');
                        paginationContainer.classList.add('pagination-controls');

                        const prevButton = document.createElement('button');
                        prevButton.innerHTML = "⬆";
                        prevButton.disabled = data.page === 0;
                        prevButton.addEventListener('click', () => fetchSuggestions(query, target, currentPage - 1));

                        const nextButton = document.createElement('button');
                        nextButton.innerHTML = "⬇";
                        nextButton.disabled = data.page >= data.totalPages - 1;
                        nextButton.addEventListener('click', () => fetchSuggestions(query, target, currentPage + 1));

                        paginationContainer.appendChild(prevButton);
                        paginationContainer.appendChild(nextButton);
                        suggestionsContainer.appendChild(paginationContainer);
                    } else {
                        const noResultMessage = document.createElement('div');
                        noResultMessage.classList.add('suggestion-item');
                        noResultMessage.textContent = 'Tidak ada hasil ditemukan.';
                        suggestionsContainer.appendChild(noResultMessage);
                    }
                })
                .catch(error => {
                    console.error('Terjadi kesalahan saat mengambil data:', error);
                    suggestionsContainer.innerHTML = '<div class="suggestion-item">Terjadi kesalahan saat memuat data.</div>';
                    isLoading = false;
                });
        }

        document.querySelectorAll('input').forEach(inputField => {
            inputField.addEventListener('input', () => {
                const query = inputField.value;
                const target = inputField.classList[0].replace('search-', '');
                fetchSuggestions(query, target);
            });
        });

        document.querySelectorAll('.search-icon').forEach(searchIcon => {
            searchIcon.addEventListener('click', () => {
                const target = searchIcon.getAttribute('data-target');
                const query = document.querySelector(`input#kodeTemplate1`).value;
                fetchSuggestions(query, target);
            });
        });

        document.addEventListener('click', function (event) {
            const isClickInside = event.target.closest('.search-container');
            if (!isClickInside) {
                document.querySelectorAll('.suggestions').forEach(container => {
                    container.innerHTML = '';
                });
            }
        });
    });
})();
