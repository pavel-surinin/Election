var CandidatesListViewComponent = React.createClass({

  render: function() {
    return (
      <div className="panel panel-default">
        <div className="panel-heading"><h2>Kandidatų sąrašas</h2></div>
          <div className="panel-body">
          </div>
            <table className="table table-striped">
            <thead>
            <tr>
              <th>
                Eil. Nr.
              </th>
              <th>
                Vardas
              </th>
              <th>
                Pavardė
              </th>
              <th>
                Partinė priklausomybė
              </th>
              <th>
                Informacija
              </th>
              <th>
                Veiksmai
              </th>
            </tr>
            </thead>
            <tbody>
			  <CandidatesListViewRowComponent name="Sigita" surname="Sigitaite" party="TS-LKD"/>
			  <CandidatesListViewRowComponent name="Marija" surname="Marijaite" party="LRLS"/>
            </tbody>
          </table>
        </div>
    );
  }
});

window.CandidatesListViewComponent = CandidatesListViewComponent;