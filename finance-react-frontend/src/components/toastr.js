import toastr from 'toastr';

toastr.options = {
    "closeButton": true,
    "debug": false,
    "newestOnTop": false,
    "progressBar": true,
    "positionClass": "toast-top-right",
    "preventDuplicates": true,
    "onclick": null,
    "showDuration": "300",
    "hideDuration": "1000",
    "timeOut": "5000",
    "extendedTimeOut": "1000",
    "showEasing": "swing",
    "hideEasing": "linear",
    "showMethod": "fadeIn",
    "hideMethod": "fadeOut"
}

export function showMessage(title, msg, type) {
    toastr[type](msg, title)
}


export function messageError(msg) {
    showMessage('Error ', msg, 'error')
}

export function messageSuccess(msg) {
    showMessage('Sucesso ', msg, 'success')
}

export function messageWarning(msg) {
    showMessage('Alerta ', msg, 'warning')
}