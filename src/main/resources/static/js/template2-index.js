(() => {
    document.addEventListener("DOMContentLoaded", function () {
        function updateFilterInput(filterSelect) {
            let filterContainer = filterSelect.closest(".filter-input");
            let searchText = filterContainer.querySelector(".search-filter[type='text']");
            let searchDate = filterContainer.querySelector(".search-filter[type='date']");
            let statusContainer = filterContainer.querySelector(".input-radio");

            // Reset tampilan input
            searchText.style.display = "none";
            searchText.removeAttribute("name");

            searchDate.style.display = "none";
            searchDate.removeAttribute("name");

            statusContainer.style.display = "none";
            statusContainer.querySelectorAll("input[type=radio]").forEach((radio) => {
                radio.removeAttribute("name");
            });

            // Menentukan input yang ditampilkan berdasarkan opsi yang dipilih
            if (["kodeTemplate1", "kode", "nama", "harga", "diskon"].includes(filterSelect.value)) {
                searchText.style.display = "block";
                searchText.name = filterSelect.name.replace("filter", "search");
            } else if (filterSelect.value === "tanggal") {
                searchDate.style.display = "block";
                searchDate.name = filterSelect.name.replace("filter", "search");
            } else if (filterSelect.value === "status") {
                statusContainer.style.display = "block";
                statusContainer.querySelectorAll("input[type=radio]").forEach((radio) => {
                    radio.name = filterSelect.name.replace("filter", "search");
                });
            }
        }

        // Event listener untuk filter pertama
        let firstFilterSelect = document.querySelector("#filter1");
        firstFilterSelect.addEventListener("change", function () {
            updateFilterInput(this);
        });

        // Event listener untuk menambahkan filter baru
        document.querySelector(".add-filter").addEventListener("click", function () {
            let filterContainer = document.querySelector(".filter-container");
            let existingFilters = filterContainer.querySelectorAll(".filter-input").length;

            if (existingFilters < 7) { // Batasi maksimal 3 filter
                let newIndex = existingFilters + 1;
                let newFilter = document.createElement("div");
                newFilter.classList.add("filter-input");
                newFilter.setAttribute("data-index", newIndex);

                newFilter.innerHTML = `
                    <button class="filter-button remove-filter" type="button"><i class="fas fa-trash-alt"></i></button>
                    <select name="filter${newIndex}" id="filter${newIndex}" class="filter-select">
                        <option value="">No Specific</option>
                        <option value="kodeTemplate1">Template 1</option>
                        <option value="kode">Kode</option>
                        <option value="nama">Nama</option>
                        <option value="tanggal">Tanggal</option>
                        <option value="harga">Harga</option>
                        <option value="diskon">Diskon</option>
                        <option value="status">Status</option>
                    </select>
                    <div class="search-container-filter">
                        <input type="text" id="searchText${newIndex}" name="search${newIndex}" class="search-filter" style="display:none;">
                        <input type="date" id="searchDate${newIndex}" name="search${newIndex}" class="search-filter" style="display:none;">
                        <div class="input-radio" id="status-container${newIndex}" style="display:none;">
                            <input type="radio" id="status-aktif${newIndex}" value="true">
                            <label for="status-aktif${newIndex}">Aktif</label>
                            <input type="radio" id="status-tidak-aktif${newIndex}" value="false">
                            <label for="status-tidak-aktif${newIndex}">Tidak Aktif</label>
                        </div>
                    </div>
                `;

                // Tambahkan event listener ke select yang baru
                let newSelect = newFilter.querySelector(".filter-select");
                newSelect.addEventListener("change", function () {
                    updateFilterInput(this);
                });

                // Tambahkan event listener ke tombol hapus filter
                newFilter.querySelector(".remove-filter").addEventListener("click", function () {
                    this.parentElement.remove();
                });

                filterContainer.appendChild(newFilter);
            }
        });

        // **PERBAIKAN TERPENTING:** Pastikan hanya mengirim input yang memiliki nilai
        document.querySelector("form").addEventListener("submit", function (event) {
            let inputs = this.querySelectorAll("input, select");
            inputs.forEach((input) => {
                if (!input.value) {
                    input.removeAttribute("name"); // Hapus input kosong agar tidak dikirim ke URL
                }
            });
        });
    });

})();