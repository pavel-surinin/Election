var alerts = {
  showDangerFixed :
  function(text){
    if (text == null || text == '') {
      return null;
    } else {
      return (
        <div className="alert alert-danger fade in alert-dismissable notification" style={{marginTop : '18px'}}>
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
        <div className="alert alert-success fade in alert-dismissable notification" style={{marginTop : '18px'}}>
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
        <div className="alert alert-success fade in alert-dismissable" style={{marginTop : '18px'}}>
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
        <div className="alert alert-info fade in alert-dismissable" style={{marginTop : '18px'}}>
          <a href="#" className="close" data-dismiss="alert" aria-label="close" title="close">×</a>
          {text}
        </div>
      );
    }
  }
};

window.alerts =alerts;
