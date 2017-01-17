
var CountyListViewComponent = React.createClass({

  render: function() {
    return (
      <div className="panel panel-default">
        <div className="panel-heading">
          <h3>Rinkimų apygardų sąrašas</h3>
        </div>
          <div className="panel-body">
            <a href="#/county/createCounty"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
            <span>
              <h5>Registruoti naują apygardą </h5>
            </span>
          </div>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>
                    Nr
                  </th>
                  <th>
                    Apygarda
                  </th>
                  <th>
                    Redaguoti
                  </th>
                </tr>
              </thead>
              <tbody>
                <CountyListRowViewComponent countyName="Vilnius" nr="1"/>
                <CountyListRowViewComponent countyName="Kaunas" nr="2"/>
                <CountyListRowViewComponent countyName="Centras" nr="3"/>
              </tbody>
            </table>
        </div>
    );
  }

});

window.CountyListViewComponent = CountyListViewComponent;
