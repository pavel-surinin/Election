var DistrictListViewComponent = React.createClass({
  render: function() {
    return (
      <div className="panel panel-default">
        <div className="panel-heading"><h2>Apylinkių sąrašas</h2></div>
          <div className="panel-body">
          </div>
            <table className="table table-striped">
            <thead>
            <tr>
              <th>
                Eil. Nr.
              </th>
              <th>
                Pavadinimas
              </th>
              <th>
                Apygarda
              </th>
              <th>
                Atstovas
              </th>
              <th>
                Veiksmai
              </th>
            </tr>
            </thead>
            <tbody>
			  <DistrictListViewRowComponent name="Pilies" county="Senamiestis" representative="M. Šilkienė"/>
			  <DistrictListViewRowComponent name="Sodų" county="Senamiestis" representative="J. Jonaitis"/>
            </tbody>
          </table>
        </div>
    );
  }

});

window.DistrictListViewComponent = DistrictListViewComponent;
