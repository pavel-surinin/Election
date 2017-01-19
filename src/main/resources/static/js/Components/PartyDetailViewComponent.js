var PartyDetailViewComponent = React.createClass({
  render: function() {
      var array = [];
      this.props.candidateidateList.map(function(candidate,index) {
        array.push(
          <PartyDetailListRowViewComponent
            id={candidate.id}
            key={index}
            name={candidate.name}
            surname={candidate.surname}
            birthDate={candidate.birthDate}
            partijosId={candidate.partijosId}
            partijosPavadinimas={candidate.partijosPavadinimas}
            description={candidate.description}
          />
        );
      });

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
                {array}
            </tbody>
          </table>
        </div>
    );
  }
});
window.PartyDetailViewComponent = PartyDetailViewComponent;
