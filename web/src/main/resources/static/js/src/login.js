/**
 *
 * Created by OAKutsenko on 01.02.2018.
 */

$(document).ready(function(){

    var patternL = 11;

    setFormValidation('#LoginForm');

    $("#phone").mask("+7 (9dd) ddd-dd-dd",{placeholder:" "});
    $("#code").mask("dddddd",{placeholder:" "})

    //Set country phone pattern
    $(".selectpicker").on('changed.bs.select', function (e) {

        var pattern = "+7 (9dd) ddd-dd-dd";
        patternL = 11;

        switch ($(".selectpicker").val()) {
            case '2':
                pattern = "+380 dd ddd-dd-dd";
                patternL = 12;
                break;
            case '3':
                pattern = "+375 dd ddd-dd-dd";
                patternL = 12;
                break;
            case '4':
                pattern = "+7 7dd ddd-dd-dd";
                break;

            case '5':
                pattern = "+370 6dd ddddd";
                break;
            default:
        }
        $("#phone").val('').mask(pattern,{placeholder:" "}).focus();
        $("#phone").parent('div').removeClass('has-error');
        $("#code").val('');
    });

    $( ':input' ).focusin(function() {}).focusout(function() {;
        $( this ).parent('div').removeClass('has-error');
    });

    $.validator.addMethod( "dataLength", function( value, element) {
        return (value.replace(/\D/g,'').length = patternL);
    }, "Invalid data length." );


    // [START appVerifier]
    window.recaptchaVerifier = new firebase.auth.RecaptchaVerifier('captcha', {
        'size': 'invisible',
        'callback': function(response) {
            // reCAPTCHA solved, allow signInWithPhoneNumber.
            onSignInSubmit();
        }
    });
    // [END appVerifier]

    recaptchaVerifier.render().then(function(widgetId) {
        window.recaptchaWidgetId = widgetId;
        updateSignInButtonUI();
    });
});

function setFormValidation(id){

    $(id).validate({
        rules: {
            phone: {
                required: true,
                rangelength: [14, 18],
                dataLength: true
            },
            code: {
                required: true,
                minlength: 6
            }
        },
        errorPlacement: function(error, element) {
            $(element).parent('div').addClass('has-error');
        },
        submitHandler: function(form) {
            var phoneNumber = $('#phone').val().replace(/\D/g,'');

            switch(findPressedButton(form)) {
                case 'code_btn':
                    switchFormState(true);
                    $("#code").focus().val('');
                    getSMSCode('+' + phoneNumber);
                    break;

                default:  // login_btn
                    var code = $('#code').val().replace(/\D/g,'');
                    onVerifyCodeSubmit(code, phoneNumber);
                    break;
            }
        }
    });
}

$("#btn_forward").click(function () {

    var phone = $("#phone");
    if(phone.val().length > 13 && phone.val().length < 19){
        switchFormState(true);
        $("#code").focus().val('');
    } else {
        phone.parent('div').addClass('has-error');
    }
});

$("#btn_back").click(function () {
    switchFormState(false);
});

/*
 Switch form state
 @false - get SMS code state
 @true - login state
 */
function switchFormState(state){

    $( "#country" ).prop( "disabled", state );
    $( "#phone" ).prop( "disabled", state );
    $( "#code" ).prop( "disabled", !state );

    $("#LoginForm").find('button[type="submit"]').each(function( index ) {
        if ($( this ).hasClass("hidden")) {
            $( this ).removeClass('hidden');
        } else {
            $( this ).addClass('hidden');
        }
    });

    if(state){
        $("#btn_forward").addClass('hidden');
        $("#btn_back").removeClass('hidden');
    } else {
        $("#btn_back").addClass('hidden');
        $("#btn_forward").removeClass('hidden');
    }
}

function findPressedButton(element){
    var result;
    $(element).find('button[type="submit"]').each(function( index ) {
        if (!$( this ).hasClass("hidden")) {
            result = this.id;
        }
    });
    return result;
}

function updateSignInButtonUI() {

    $('div.card-login').removeClass('hidden');
    $('#preloader').hide();
}

function onSignInSubmit(){
    //$('code_btn').removeClass('hidden');
}

function getSMSCode(phoneNumber){

    var appVerifier = window.recaptchaVerifier;

    firebase.auth().signInWithPhoneNumber(phoneNumber, appVerifier)
        .then(function (confirmationResult) {
            // SMS sent. Prompt user to type the code from the message, then sign the
            // user in with confirmationResult.confirm(code).

            window.confirmationResult = confirmationResult;

        }).catch(function (error) {

        // Error; SMS not sent
        updateSignInButtonUI()
        showNotification('top', 'left', error.message);
        //console.error('Error during signInWithPhoneNumber', error);
    });
}

/**
 * Function called when clicking the "Verify Code" button.
 */
function onVerifyCodeSubmit(code, phoneNumber) {


    confirmationResult.confirm(code).then(function (result) {

        // User signed in successfully.
        //var user = result.user;

        firebase.auth().currentUser.getIdToken(true).then(function(idToken) {

            login(idToken, phoneNumber);

        }).catch(function(error) {

            showErrorMsg(error);
        });

    }).catch(function (error) {

        showErrorMsg(error);

    });
}

function showErrorMsg(error) {

    showNotification('top', 'left', error.message);
    //switchFormState(false);
    $('#code').val('');
}

function showNotification(from, align, text){

    $.notify({
        icon: "error_outline",
        message: text

    },{
        type: 'danger',
        timer: 4000,
        placement: {
            from: from,
            align: align
        }
    });
}

function  login(credentials, phoneNumber) {

    $('div.card-login').addClass('hidden');
    $('#preloader').show();

    $( "input[name='phone']" ).val(phoneNumber);
    $( "input[name='credentials']" ).val(credentials);
    $('#auth').submit();
}
