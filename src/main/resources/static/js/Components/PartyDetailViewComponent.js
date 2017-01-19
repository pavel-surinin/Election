var PartyDetailViewComponent = React.createClass({
  render: function() {

    return (
      <div className="panel panel-default">
          <div className="panel-heading"><h2>Partijos pavadinimas</h2></div>
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
                  Gimimo data
                </th>
                <th>
                  Veiksmai
                </th>
              </tr>
            </thead>
            <tbody>
                <PartyDetailListRowViewComponent />
            </tbody>
          </table>
        </div>
    );
  }
});
window.PartyDetailViewComponent = PartyDetailViewComponent;
