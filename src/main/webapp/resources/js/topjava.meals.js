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
                        "data": "excess"
                    },
                    {
                        "data": "id"
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
                        "asc"
                    ]
                ]
            })
        }
    );
    /*$("#filterMeals").click(function () {
        $.ajax({
            url: "ajax/meal/filter",
            type: "GET",
            data: {
                startDate: startDate,
                startTime: startTime,
                endDate: endDate,
                endTime: endTime
            }
        });
    })*/
});

/*$(document).ready(
    function filterMeals() {
        $.ajax({
            type: "GET",
            url: "ajax/meal/filter?startDate=" + $(this).attr("startDate")
                + "&startTime=" + $(this).attr("startTime")
                + "&endDate=" + $(this).attr("endDate")
                + "&endTime=" + $(this).attr("endTime")
        });
        updateTable()
    }
)*/

/*function filterMeals() {
    $.ajax({
        type: "GET",
        url: "ajax/meal/filter?startDate=" + $(this).attr("startDate")
            + "&startTime=" + $(this).attr("startTime")
            + "&endDate=" + $(this).attr("endDate")
            + "&endTime=" + $(this).attr("endTime")
    });
    updateTable()
}*/

/*
function filterMeals() {
    $.ajax({
        type: "GET",
        url: "ajax/meal/filter" + $(this).attr("startDate") + $(this).attr("startTime") + $(this).attr("endDate") + $(this).attr("endTime")
    });
    updateTable()
}*/
