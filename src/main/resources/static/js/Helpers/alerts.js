var alerts = {
  showDangerFixed :
  function(text){
    if (text == null || text == '') {
      return null;
    } else {
      return (
        <div className="alert alert-danger fade in alert-dismissable notification" id="alert-danger-fixed" style={{marginTop : '18px'}}>
          <a href="#" className="close" data-dismiss="alert" aria-label="close" title="close">×</a>
          {text}
        </div>
      );
    }
  },
  showDanger :
  function(text){
    if (text == null || text == '') {
      return null;
    } else {
      return (
        <div className="alert alert-danger fade in alert-dismissable" id="alert-danger" style={{marginTop : '18px'}}>
          <a href="#" className="close" data-dismiss="alert" aria-label="close" title="close">×</a>
          {text}
        </div>
      );
    }
  },
  showSuccesFixed :
  function(text){
    if (text == null || text == '') {
      return null;
    } else {
      return (
        <div className="alert alert-success fade in alert-dismissable notification" id="alert-success-fixed" style={{marginTop : '18px'}}>
          <a href="#" className="close" data-dismiss="alert" aria-label="close" title="close">×</a>
          {text}
        </div>
      );
    }
  },
  showSucces :
  function(text){
    if (text == null || text == '') {
      return null;
    } else {
      return (
        <div className="alert alert-success fade in alert-dismissable" id="alert-success" style={{marginTop : '18px'}}>
          <a href="#" className="close" data-dismiss="alert" aria-label="close" title="close">×</a>
          {text}
        </div>
      );
    }
  },
  showInfo :
  function(text){
    if (text == null || text == '') {
      return null;
    } else {
      return (
        <div className="alert alert-info fade in alert-dismissable" id="alert-info" style={{marginTop : '18px'}}>
          <a href="#" className="close" data-dismiss="alert" aria-label="close" title="close">×</a>
          {text}
        </div>
      );
    }
  }
};

window.alerts =alerts;
