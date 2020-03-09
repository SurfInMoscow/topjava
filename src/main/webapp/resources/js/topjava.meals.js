var mealAjaxUrl = "ajax/meal/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/meal/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("ajax/meal/", updateTableByData);
}

// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: mealAjaxUrl,
            datatableApi: $("#datatable").DataTable({
                "ajax": {
                    "url": mealAjaxUrl,
                    "dataSrc": ""
                },
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "datetime",
                        "render": function (data) {
                            return new Date(data);
                        }
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderEditBtn
                    },
                    {
                        "orderable": false,
                        "defaultContent": "",
                        "render": renderDeleteBtn
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ],
            }),
        updateTable: function () {
            $.get(mealAjaxUrl, updateTableByData);
        }
    });
});