
var GeneralresultsComponent = React.createClass({
  componentWillReceiveProps: function(nextProps) {
    if (nextProps.visible !== this.props.visible) {
      if (nextProps.visible) {
        $(findDOMNode(this)).stop( true, true ).fadeIn('slow');
      } else {
        $(findDOMNode(this)).stop( true, true ).fadeOut('slow');
      }
    }
  },
  render: function() {
    console.log('GeneralresultsComponent');
    console.log(this);
    return (

      <div className='container'>
        <h1 className='yellow'>GeneralresultsComponent</h1>
        <div className="panel-group" id="accordion" role="tablist" aria-multiselectable="false">
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingOne">
              <h4 className="panel-title">
                <a role="button" data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                  Partijų grafikas (gyvai)
                </a>
              </h4>
            </div>
            <div id="collapseOne" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
              <div className="panel-body">
                Superlivegrafikas ouououou
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingTwo">
              <h4 className="panel-title">
                <a className="collapsed" role="button" data-toggle="collapse" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                  Kiek praejo bendrai pagal partija grafikas
                </a>
              </h4>
            </div>
            <div id="collapseTwo" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
              <div className="panel-body">
                Grafikas kuris parodo kiek partija turi nariu seime.
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingThree">
              <h4 className="panel-title">
                <a className="collapsed" role="button" data-toggle="collapse" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                  Rinkimu eiga
                </a>
              </h4>
            </div>
            <div id="collapseThree" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                progress barai spurgos
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingFour">
              <h4 className="panel-title">
                <a className="collapsed" role="button" data-toggle="collapse" href="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
                  Daugiamandates isrinktuju sarasas
                </a>
              </h4>
            </div>
            <div id="collapseFour" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                Kas praejo daugiamandateje.
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingFive">
              <h4 className="panel-title">
                <a className="collapsed" role="button" data-toggle="collapse" href="#collapseFive" aria-expanded="false" aria-controls="collapseThree">
                  Galutinis sarasas
                </a>
              </h4>
            </div>
            <div id="collapseFive" className="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                Sarasas su visais seimunais.
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }

});

window.GeneralresultsComponent = GeneralresultsComponent;
