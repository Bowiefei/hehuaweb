var Message = Message || {
  info : function(msg, auto_close) {
    if ($('#message').length > 0) {
      $('#message').remove();
    }

    var $target = $('#module-head');
    if ($target.length == 0) {
      $target = $('.module');
    }
    $target.after('<div id="message" style="display: none;">' + msg + '</div>');
    $('#message').addClass('alert').addClass('alert-info').slideDown(300);
    if (auto_close) {
      setTimeout(function() {
        $('#message').slideUp(300);
      }, 5000);
    }
  },
  error : function(msg, auto_close) {
    if ($('#message').length > 0) {
      $('#message').remove();
    }

    var $target = $('#module-head');
    if ($target.length == 0) {
      $target = $('.module');
    }
    $target.after('<div id="message" style="display: none;">' + msg + '</div>');
    $('#message').addClass('alert').addClass('alert-danger').slideDown(300);
    if (auto_close) {
      setTimeout(function() {
        $('#message').slideUp(300);
      }, 5000);
    }
  }
};