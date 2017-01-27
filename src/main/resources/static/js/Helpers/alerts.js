var alerts = {
  showSucces : function(text){
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
  }
};

window.alerts =alerts;
