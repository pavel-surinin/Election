var PartyListViewComponent = React.createClass({

  render: function() {
    return (
      <div className="panel panel-default">
        <div className="panel-heading">
          <h3>Partijų sąrašas</h3>
        </div>
          <div className="panel-body">
            <a href="#/party/createParty"><button type="button" className="btn btn-success btn-sm">Registruoti</button></a>
            <span>
              <h5>Registruoti naują partiją </h5>
            </span>
          </div>
            <table className="table table-striped">
              <thead>
                <tr>
                  <th>
                    Nr
                  </th>
                  <th>
                    Partija
                  </th>
                  <th>
                    Redaguoti
                  </th>
                </tr>
              </thead>
              <tbody>
                <PartyListViewRowComponent partyName="Vilnius" nr="1"/>
                <PartyListViewRowComponent partyName="Kaunas" nr="2"/>
                <PartyListViewRowComponent partyName="Centras" nr="3"/>
              </tbody>
            </table>
        </div>
    );
  }

});

window.PartyListViewComponent = PartyListViewComponent;
