(() => {
    document.addEventListener('DOMContentLoaded', function() {
        let mainUrl = "http://localhost:8081/api/template2";
        let isLoading = false;

        function fetchSuggestions(query = '', target) {
            if (isLoading) return;
            isLoading = true;

            const suggestionsContainer = document.querySelector(`.suggestions.kodeTemplate1`);
            suggestionsContainer.innerHTML = '<div class="loading-spinner">Memuat...</div>';

            fetch(`${mainUrl}/kodeTemplate1-options`)
                .then(response => response.json())
                .then(data => {
                    isLoading = false;
                    let filteredData = [];

                    filteredData = query
                        ? data.filter(item => item.name.toLowerCase().includes(query.toLowerCase()))
                        : data;

                    suggestionsContainer.innerHTML = '';

                    if (filteredData.length > 0) {
                        filteredData.forEach(item => {
                            const div = document.createElement('div');
                            div.classList.add('suggestion-item');
                            div.textContent = `${item.kode} - ${item.name}`;

                            div.addEventListener('click', function () {
                                const inputField = document.querySelector(`input#kodeTemplate1`);
                                inputField.value = `${item.kode} - ${item.name}`;

                                suggestionsContainer.innerHTML = '';
                            });

                            suggestionsContainer.appendChild(div);
                        });
                    } else {
                        const noResultMessage = document.createElement('div');
                        noResultMessage.classList.add('suggestion-item');
                        noResultMessage.textContent = 'Tidak ada hasil ditemukan.';
                        suggestionsContainer.appendChild(noResultMessage);
                    }
                })
                .catch(error => {
                    console.error('Terjadi kesalahan saat mengambil data:', error);
                    const suggestionsContainer = document.querySelector(`.suggestions.kodeTemplate1`);
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

        document.addEventListener('click', function(event) {
            const isClickInside = event.target.closest('.search-container');
            if (!isClickInside) {
                document.querySelectorAll('.suggestions').forEach(container => {
                    container.innerHTML = '';
                });
            }
        });
    });
})();