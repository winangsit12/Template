(() => {
    document.addEventListener("DOMContentLoaded", function () {
        function updateFilterInput(filterSelect) {
            let filterContainer = filterSelect.closest(".filter-input");
            let searchInput = filterContainer.querySelector(".search-filter");
            let statusContainer = filterContainer.querySelector(".input-radio");

            searchInput.style.display = "none";
            statusContainer.style.display = "none";

            if (filterSelect.value === "kode" || filterSelect.value === "nama") {
                searchInput.style.display = "block";
                searchInput.name = filterSelect.name.replace("filter", "search");
            } else if (filterSelect.value === "status") {
                statusContainer.style.display = "block";

                statusContainer.querySelectorAll("input[type=radio]").forEach((radio) => {
                    radio.name = filterSelect.name.replace("filter", "search");
                });
            }
        }

        let firstFilterSelect = document.querySelector("#filter1");
        firstFilterSelect.addEventListener("change", function () {
            updateFilterInput(this);
        });

        document.querySelector(".add-filter").addEventListener("click", function () {
            let filterContainer = document.querySelector(".filter-container");
            let existingFilters = filterContainer.querySelectorAll(".filter-input").length;

            if (existingFilters < 3) {
                let newIndex = existingFilters + 1;
                let newFilter = document.createElement("div");
                newFilter.classList.add("filter-input");
                newFilter.setAttribute("data-index", newIndex);

                newFilter.innerHTML = `
                    <button class="filter-button remove-filter" type="button"><i class="fas fa-trash-alt"></i></button>
                    <select name="filter${newIndex}" id="filter${newIndex}" class="filter-select">
                        <option value="">No Specific</option>
                        <option value="kode">Kode</option>
                        <option value="nama">Nama</option>
                        <option value="status">Status</option>
                    </select>
                    <div class="search-container-filter">
                        <input type="text" id="search${newIndex}" name="search${newIndex}" class="search-filter" style="display:none;">
                        <div class="input-radio" id="status-container${newIndex}" style="display:none;">
                            <input type="radio" id="status-aktif${newIndex}" value="true">
                            <label for="status-aktif${newIndex}">Aktif</label>
                            <input type="radio" id="status-tidak-aktif${newIndex}" value="false">
                            <label for="status-tidak-aktif${newIndex}">Tidak Aktif</label>
                        </div>
                    </div>
                `;

                let newSelect = newFilter.querySelector(".filter-select");
                newSelect.addEventListener("change", function () {
                    updateFilterInput(this);
                });

                newFilter.querySelector(".remove-filter").addEventListener("click", function () {
                    this.parentElement.remove();
                });

                filterContainer.appendChild(newFilter);
            }
        });

        document.querySelector("form").addEventListener("submit", function (event) {
            let inputs = this.querySelectorAll("input, select");
            inputs.forEach((input) => {
                if (!input.value) {
                    input.removeAttribute("name");
                }
            });
        });
    });

})()