function getSingleWinners(self){
  var singleWinners = [];
  if (self.props.results.singleWinners){
    singleWinners = self.props.results.singleWinners.map(function(sid,index){
      return(
        <tr key={index}>
          <td>{sid.candidate.name + ' ' + sid.candidate.surname}</td>
          <td>{sid.candidate.partijosPavadinimas}</td>
          <td>{sid.candidate.countyName}</td>
          <td>{sid.votes}</td>
        </tr>
      );
    });
  }
    return singleWinners;
}
function showSingleWinners(self){
  var singleWinners = getSingleWinners(self);
  if(self.props.results){
    if(self.props.results.districtsVoted == self.props.results.districtsCount){
      return(
        <div className="panel panel-default" style={{borderRadius: '1px'}}>
          <div className="panel-heading" role="tab" id="headingSix">
            <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                href="#collapseSix" aria-expanded="false" aria-controls="collapseSix" ref='title' id='heading'>
              Vienmandates nugaletoju sarasas
              <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
            </h4>
          </div>
          <div id="collapseSix" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
            <div className="panel-body">
              <table className="table table-striped">
                <thead>
                <tr>
                  <th className='col-md-4 col-sm-4'>Kandidatas</th>
                  <th className='col-md-3 col-sm-3'>Partija</th>
                  <th className='col-md-3 col-sm-3'>Apygarda</th>
                  <th className='col-md-2 col-sm-2'>Balsai</th>
                </tr>
                </thead>
                <tbody>
                {singleWinners}
                </tbody>
              </table>
              <div className="pull-right">
                <i className="fa fa-compress" role="button" data-toggle="collapse"
                   href="#collapseSix" aria-expanded="false" aria-controls="collapseSix" style={{transform: 'rotate(-45deg)'}}></i>
              </div>
            </div>
          </div>

        </div>
      );
      } else {
        return null;
      }
    } else {
    return null;
  }

  }
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
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                  Partijų grafikas (gyvai)
                <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>
            <div id="collapseOne" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
              <div className="panel-body">
                Superlivegrafikas ouououou
              </div>
            </div>
          </div>

          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingTwo">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                  Kiek praejo bendrai pagal partija grafikas
                <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>

            <div id="collapseTwo" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
              <div className="panel-body">
                Grafikas kuris parodo kiek partija turi nariu seime.
              </div>
            </div>
          </div>

          <div className="panel panel-default" style={{borderRadius: '1px'}}>
            <div className="panel-heading" role="tab" id="headingThree">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                  Rinkimu eiga
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>
            <div id="collapseThree" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
              <div className="panel-body">
                progress barai spurgos
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>

            <div className="panel-heading" role="tab" id="headingFour">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                   href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                  Daugiamandates isrinktuju sarasas
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>

              </h4>
            </div>
            <div id="collapseFour" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
              <div className="panel-body">
                Kas praejo daugiamandateje.
              </div>
            </div>
          </div>
          <div className="panel panel-default" style={{borderRadius: '1px'}}>

            <div className="panel-heading" role="tab" id="headingFive">
              <h4 className="panel-title collapsed" role="button" data-toggle="collapse"
                  href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                  Galutinis sarasas
                  <i className="fa fa-sort-desc pull-right icon-transition" aria-hidden="true" ref='icon'></i>
              </h4>
            </div>
            <div id="collapseFive" className="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
              <div className="panel-body">
                Sarasas su visais seimunais.
              </div>
            </div>
          </div>
          {showSingleWinners(this)}
        </div>
      </div>
    );

  }

});

window.GeneralresultsComponent = GeneralresultsComponent;
