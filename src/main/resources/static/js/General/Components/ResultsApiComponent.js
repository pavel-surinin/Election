var ResultsApiComponent = React.createClass({
 componentDidMount: function () {
     $(this.refs.info).tooltip();
    $(this.refs.info).click(function(){
    	$("[data-toggle='tooltip']").tooltip({title: "Nuoroda nukopijuota"});
      $("[data-toggle='tooltip']").tooltip('toggle');
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
    return (
         <div>
              <button className="btn btn-success btn-sm" onClick={this.onShowHide}>
                Web Servisas
                <span className="caret"></span>
              </button>
              <div className="panel" id="WebApiDiv" style={{width: '285px', display: 'none', position: 'absolute', padding: '5px'}}>
                <div>
                  <h5>
                    Rezultatų Web Serviso Api nuoroda
                  </h5>
                  <div className="input-group input-group-sm">
                    <input type="text" id="WebApi" className="form-control" value="http://localhost:9090/results/general" readOnly>
                    </input>
                    <span className="input-group-btn" ref="info" data-toggle="tooltip">
                        <button onClick={this.onCopy} className="btn btn-default fa fa-floppy-o fa-4" ref="info" data-toggle="tooltip" title="Kopijuoti nuorodą" type="button"></button>
                    </span>
                  </div>
                </div>
              </div>
        </div>
    );
  }

});

window.ResultsApiComponent = ResultsApiComponent;
