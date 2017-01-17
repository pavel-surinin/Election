var DistrictRepresentativeComponent = React.createClass({
  render: function() {
    return (
      <div className="panel panel-default">
        <div className="panel-heading">
          <h3>Apylinkės Atstovų sarašas</h3>
        </div>
          <div className="panel-body">
            <a href="#/rep/createRep"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
            <span>
              <h5>Registruoti naują apylinkės atstovą </h5>
            </span>
          </div>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>
                    Nr
                  </th>
                  <th>
                    Vardas
                  </th>
                  <th>
                    Pavarde
                  </th>
                  <th>
                    Atstovaujama Apylinkė
                  </th>
                  <th>
                    Redaguoti
                  </th>
                </tr>
              </thead>
              <tbody>
                <DistrictRespesentativeListViewRowComponent name="Jonas" nr="1"/>
                <DistrictRespesentativeListViewRowComponent name="Petras" nr="2"/>
                <DistrictRespesentativeListViewRowComponent name="As" nr="3"/>
              </tbody>
            </table>
        </div>
    );
  }
});

window.DistrictRepresentativeComponent = DistrictRepresentativeComponent;
