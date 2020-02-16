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
            ajaxUrl: "ajax/meal/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "datetime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            }),
            updateTable: updateFilteredTable
        });
});
