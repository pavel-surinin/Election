var HomeComponent = React.createClass({

  render: function() {
    document.title='Rinkimai 2017';
    return (
      <div>
        <div className="container">
          <h1 className='yellow'>Rinkimai 2017</h1>
        </div>
        <div id="exTab1" className="container shadow">
          <div className="tab-content clearfix">

            <div className="col-xs-12 col-sm-6 col-md-4">
              <h2>Rezultaitai CSV</h2>
              <a target="_blank" href="results/csv/single">Vienmandačių rezultaitai</a> <br/>
              <a target="_blank" href="results/csv/multi/winners">Daigiamandačių laimėtojai</a><br/>
              <a target="_blank" href="results/csv/multi/votes">Daugiamandačių balsai</a><br/>
              <a target="_blank" href="results/csv/mandates">Kiek partijos gavo mandatų daugiamandatėse</a><br/>
              <a target="_blank" href="results/csv/mandates/general">Kiek partijos gavo mandatų</a><br/>
            </div>

            <div className="col-xs-12 col-sm-6 col-md-4">
              <h2>Web serviso api</h2>
              <a target="_blank" href="results/general">Rezultaitai (json)</a> <br/>
            </div>

            <div className="col-xs-12 col-sm-6 col-md-4">
              <h2>Rinkimų eiga</h2>
              Prabalsavo rinkėjų 10 iš 100<br/>
              Prabalsavo apylinkių 10 iš 100<br/>
              Užregitruota Apygardų <br/>
              Užregitruota Apylinkių <br/>
              Užregitruota Partijų <br/>
              Užregitruota Kandidatų <br/>
            </div>
            <br/>
          </div>
        </div>
      </div>
    );
  }

});

window.HomeComponent = HomeComponent;
