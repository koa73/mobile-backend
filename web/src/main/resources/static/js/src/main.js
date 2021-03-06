/**
 *
 * Created by OAKutsenko on 10.03.2018.
 */

$(document).ready(function(){

    var locale = navigator.language || navigator.userLanguage || navigator.systemLanguage || 'ru';

    $('ul.nav li').click(function(e)
    {
        var selected = $(this);
        if ($(this).attr('id') != null){
            $.ajax({
                url: "/"+$(this).attr('id') ,
                success: function( response, status, xhr) {

                    if (xhr.getResponseHeader('Location') == null){
                        window.location = document.location.origin+"/login";
                    } else {
                        $('div.row').html(response);
                        $('li.active').removeClass('active');
                        selected.addClass('active');

                    }
                },
                complete: function ( xhr, status) {
                    attacheEventHandler(xhr.getResponseHeader('Location'));
                }
            });
        } 
    });


    function attacheEventHandler(location) {

        switch (location.replace('/','')) {

            case 'operations':

                $('#datatables').DataTable({
                    ajax: {
                        url: '/data/operations',
                        dataSrc: function (json) {

                            if (json.data.length != null){
                                $("#datatables").find('tfoot').show();
                            }
                            return json.data;

                        }
                    },
                    language: {
                        url: '/locale/dt/'+locale+'.json'
                    },
                    "aoColumnDefs": [
                        { "sType": "date-ru",  "aTargets": [ 7 ]  }
                    ],
                    "columns": [
                        { "data": "type" , render: function (data, type, row) {
                            return operations[data];
                        }},
                        { "data": "m_src", "orderable": false, render: function (data, type, row) {
                            return phoneFormatter(data);
                        }},
                        { "data": "m_dst", "orderable": false, render: function (data, type, row) {
                            return phoneFormatter(data);
                        }},
                        { "data": "amount", "orderable": false,render: function (data, type, row) {
                            return formatter.format(data);
                        }},
                        { "data": "fee", "orderable": false, render: function (data, type, row) {
                            return formatter.format(data);
                        }},
                        { "data": "bank", "orderable": false },
                        { "data": "t_id", "orderable": false},
                        { "data": "time"}
                    ],
                    "order": [[ 7, "desc" ]]
                });
                break;

            case 'profile':
                $('#uname').val($("#user").html());
                break;

            case 'settings':
                linkHandler();
                break;
        }
    }

    var formatter = new Intl.NumberFormat('ru', {
        style: 'currency',
        currency: 'RUB',
        minimumFractionDigits: 2
    });

    function phoneFormatter(data) {
        
        if (data.search(/\D/i) != -1){
            if (data.length == 4) {
                return data.replace('*', '*** **** **** **');
            } else {
                return data.replace('*', ' **** **** ');

            }
        } else {

            if (data.match(/^79/i) ){
                //RU
                return data.replace(/^\d{2}(\d{2})(\d{3})(\d{2})(\d{2})$/, "+7 (9$1) $2-$3-$4");

            } else if (data.match(/^380/i) ){
                //UA
                return data.replace(/^\d{3}(\d{2})(\d{3})(\d{2})(\d{2})$/, "+380 $1 $2-$3-$4");

            } else if (data.match(/^375/i) ){
                //BL
                return data.replace(/^\d{3}(\d{2})(\d{3})(\d{2})(\d{2})$/, "+375 $1 $2-$3-$4");

            } else if (data.match(/^77/i) ) {
                //KZ
                return data.replace(/^\d{2}(\d{2})(\d{3})(\d{2})(\d{2})$/, "+7 7$1 $2-$3-$4");
            } else if (data.match(/^3706/i) ) {
                //LT
                return data.replace(/^\d{3}(\d{3})(\d{5})$/, "+370 $1 $2");
            }
            return data;
        }
    }

    $("#user").html(phoneFormatter($("#user").html())).show();
});

<!-- Get form value as JSON -->
function getFormData(fomName){
    var paramObj = {};
    $.each($(fomName).find('input, select').serializeArray(), function(_, kv) {
        if (paramObj.hasOwnProperty(kv.name)) {
            paramObj[kv.name] = $.makeArray(paramObj[kv.name]);
            paramObj[kv.name].push(kv.value);
        }
        else {
            paramObj[kv.name] = kv.value;
        }
    });
    return JSON.stringify(paramObj);
}

$.ajaxSetup({
    beforeSend: function(xhr, settings) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        if (token != null && header != null){
            xhr.setRequestHeader(header, token);
        }
    }
});

function showNotification(from, align, text){

    $.notify({
        icon: "error_outline",
        message: text

    },{
        type: 'danger',
        timer: 10000,
        placement: {
            from: from,
            align: align
        }
    });
}

jQuery.extend( jQuery.fn.dataTableExt.oSort, {
    "date-ru-pre": function ( a ) {
        return a.replace(/^(\d{2})\/(\d{2})\/(\d{4})/, "$3$2$1");
    },

    "date-ru-asc": function ( a, b ) {
        return ((a < b) ? -1 : ((a > b) ? 1 : 0));
    },

    "date-ru-desc": function ( a, b ) {
        return ((a < b) ? 1 : ((a > b) ? -1 : 0));
    }
} );

function linkHandler(){

    function setLinkFormValidation(id){

        $(id).validate({
            rules: {
                sum: {
                    min: 100,
                    max: 100000
                },
                text: {
                    required: true,
                    maxlength: 29
                }
            },
            errorPlacement: function(error, element) {
                $(element).parent('div').addClass('has-error');
            },
            submitHandler: function(form) {

                console.log(getFormData(form));

                $.ajax({
                    url: "/data/url" ,
                    type: 'POST',
                    data: getFormData(form),
                    beforeSend: function (xhr) {

                        xhr.setRequestHeader("Accept", "application/json");
                        xhr.setRequestHeader("Content-Type", "application/json");
                        var token = $("meta[name='_csrf']").attr("content");
                        var header = $("meta[name='_csrf_header']").attr("content");
                        if (token != null && header != null){
                            xhr.setRequestHeader(header, token);
                        }
                    },
                    success: function( response, status, xhr) {
                        $("#item").html('URL');
                        $('#btn_create').addClass('hidden')
                        $('#btn_copy').removeClass('hidden');
                        $('div.info-content').html('').append('<textarea readonly style="width: 100%; height: 100%; ' +
                            'border: none; resize: none; " id="url"></textarea>');
                        $('#url').html(response.url);
                        $("input, div.bootstrap-select").each(function(){
                            $(this).attr('disabled' ,true).unbind( "click" );
                        });
                        $('button.btn-trans').attr('disabled' ,true);
                    }
                });
            }
        });
    }

    setLinkFormValidation('#LinkForm');

    //Set country phone pattern
    $(".selectpicker").selectpicker('refresh').on('changed.bs.select', function (e) {
        $("#btn_create").attr('disabled' , !($(".selectpicker").val().length > 1));
    });

    if ($(".selectpicker option").length < 2){
        showNotification('top', 'left', error_not_reg);
    }

    $('div.info-content').html(info.main);

    $("input, div.bootstrap-select").each(function(){

        $(this).click(function(){

            var item = '';
            if($(this).data("item")){
                item = $(this).data("item");
            } else {
                item = $(this).siblings('label').text();
            }
            $("#item").html(item);
            $('div.info-content').html('<p>'+info[item]+'</p>');
        });
    });


    $("#btn_copy").click(function () {
        /* Select the text field */
        $('#url').select();
        /* Copy the text inside the text field */
        document.execCommand("Copy");
        document.getSelection().removeAllRanges();
    });
}


