function createMatrixTable() {
    var box = $("#box");
    if ($.trim(box.html()).length > 0) {
        box.empty();
    }
    var table = $("<table class='matrix-table table table-striped'></table>");
    var rows = Number($("#rowcount").val());
    var cols = Number($("#columncount").val());
    if (rows <= 0 || cols <= 0) {
        alert("Matrix column and row more than 0");
    } else {
        for (var i = 0; i <= rows - 1; i++) {
            var row = $('<tr></tr>').attr({class: ["class"].join(' ')}).appendTo(table);
            for (var j = 0; j < cols; j++) {
                $('<td></td>').html('<input type="number" value="0" class="form-control text-' + i + j + '"/>').appendTo(row);
            }
        }
        table.appendTo(box);
    }
}

function getMatrixValueInTable() {
    var tRows = $('tr').map(function () {
        return [$(this).find('td input').map(function () {
            return $(this).val();
        }).get()]
    }).get();
    /*<![CDATA[*/
    var path = /*[[@{/}]]*/'matrix';
    /*]]>*/
    var column = $("#columncount").val();
    var row = $("#rowcount").val();
    var typeMatrix = $('input[name=matrix]:checked').val();
    if (typeMatrix === "identity") {
        if (row !== column) {
            alert("Матрица должна быть квадратичной!!!");
            return;
        }
    }
    var data = {
        "arr": tRows,
        "typeMatrix": typeMatrix
    };
    $.ajax({
        type: 'POST',
        url: path,
        data: JSON.stringify(data),
        traditional: true,
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            alert("Success, Solve in result.csv");
            if (typeMatrix === "sparse") {
                location.reload();
            }

        },
        error: function (res) {
            alert("Задайте правильный размер массива!!!");
        }
    });
}

function uploadFileToResource() {
    /*<![CDATA[*/
    var path = /*[[@{/}]]*/'upload';
    var formData = new FormData();
    var file = $('input[type=file]');
    formData.append("file", file[0].files[0]);

    $.ajax({
        type: 'POST',
        enctype: 'multipart/form-data',
        url: path,
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
        success: function (res) {
            alert("Success upload");
        },
        error: function (res) {
            alert("Error upload file")
        }
    });
}

function gaussMethod() {
    var Gmatrix = $('.gauss-table > tr').map(function () {
        return [$(this).find('td input').map(function () {
            return $(this).val();
        }).get()]
    }).get();

    var Gvalues = $('.values-table > tr > td > input').map(function () {
        return $(this).val()
    }).get();

    var data = {
        "matrix": Gmatrix,
        "values": Gvalues
    };

    var path = 'gauss/solve';

    $.ajax({
        type: 'POST',
        url: path,
        data: JSON.stringify(data),
        traditional: true,
        contentType: "application/json; charset=utf-8",
        success: function (res) {
            alert("Success!!! Result in result.csv");
        },
        error: function (res) {
            alert("Задайте правильный размер массива!!!")
        }
    });
}

function createGaussMatrix() {
    var matrix = $("#matrix");
    var values = $("#values");
    if ($.trim(matrix.html()).length > 0 || $.trim(values.html()).length > 0) {
        matrix.empty();
        values.empty();
    }
    var rows = Number($("#rowcount").val());
    var cols = Number($("#columncount").val());

    var gauss_table = $("<table class='gauss-table table table-striped'></table>");

    if (rows <= 0 || cols <= 0) {
        alert("More than 0");
    } else {
        for (var i = 0; i <= rows - 1; i++) {
            var row = $('<tr></tr>').attr({class: ["class"].join(' ')}).appendTo(gauss_table);
            for (var j = 0; j < cols; j++) {
                $('<td></td>').html('<input type="number" value="0" class="form-control text-' + i + j + '"/>').appendTo(row);
            }
        }
        gauss_table.appendTo(matrix);
    }
    var values_table = $("<table class='values-table table table-striped'></table>");
    var rowG = $('<tr></tr>').attr({class: ["class"].join(' ')}).appendTo(values_table);
    for (var g = 0; g <= rows - 1; g++) {
        $('<td></td>').html('x' + (g + 1) + '<input type="number" value="0" class="form-control text-' + g + '"/>').appendTo(rowG);
    }
    values_table.appendTo(values);
}

$(document).ready(function () {

    $("input:radio[name=matrix]").change(function () {
        if (this.value === "stat") {
            $("#file-upload").css("display", "flex");
        } else {
            $("#file-upload").css("display", "none");
        }

    });
    $("#create-gauss-matrix").on("click", function (e) {
        e.preventDefault();
        createGaussMatrix();
    });
    $("#get-gauss-values").on("click", function (e) {
        e.preventDefault();
        gaussMethod();
    });
    $("#fileUploadForm").on("submit", function (e) {
        e.preventDefault();
        uploadFileToResource();
    });

    $("#create-matrix").on('click', function () {
        createMatrixTable();
    });

    $("#get-matrix-values").on('click', function () {
        getMatrixValueInTable();
    });

});