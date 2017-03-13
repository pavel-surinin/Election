var ResultsApiComponent = React.createClass({
 componentDidMount: function () {
    $(this.refs.info).tooltip({title: 'Kopijuoti nuorodą', trigger: 'hover'});
    $(this.refs.info).click(function(){
       $("[data-toggle='tooltip']").tooltip('hide');
         });
    $(this.refs.infoCopy).tooltip({title: 'Nuoroda nukopijuota!',trigger: 'click'});
    $(this.refs.infoCopy).click(function(){
      setTimeout(function(){
          $("[data-toggle='tooltip1']").tooltip('hide');
      }, 2000);
      });
 },
 onShowHide: function () {
   var webDiv = document.getElementById('WebApiDiv');
    if (webDiv.style.display === 'none') {
        webDiv.style.display = 'block';
    } else {
        webDiv.style.display = 'none';
    }
 },
 onCopy: function () {
    var WebApiLink = document.getElementById('WebApi');
      WebApiLink.focus();
      WebApiLink.setSelectionRange(0, WebApiLink.value.length);
    var copysuccess
   try{
       copysuccess = document.execCommand("copy");
   } catch(e){
       copysuccess = false;
   }
 },
  render: function() {
    var apiLink = 'http://localhost:9090/results/'+this.props.territory+'/'+this.props.id;
    return (
         <span>
              <button className="btn btn-success btn-sm" id="WebApiButton" onClick={this.onShowHide}>
                Rezultatai Web servisui
              </button>
              <div className="panel" id="WebApiDiv" style={{display: 'none'}}>
                <div className="panel">
                  <h5>
                    Rezultatų Web Serviso Api nuoroda
                  </h5>
                  <div className="input-group input-group-sm">
                    <input type="text" id="WebApi" className="form-control" value={apiLink} readOnly>
                    </input>
                    <span className="input-group-btn" ref="info" data-toggle="tooltip" >
                        <button onClick={this.onCopy} className="btn btn-default fa fa-floppy-o fa-4" ref="infoCopy" data-toggle="tooltip1" type="button"></button>
                    </span>
                  </div>
                </div>
              </div>
        </span>
    );
  }

});

window.ResultsApiComponent = ResultsApiComponent;
