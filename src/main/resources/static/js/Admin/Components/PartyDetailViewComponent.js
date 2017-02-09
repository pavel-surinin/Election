var PartyDetailViewComponent = React.createClass({
  render: function() {
      var array = [];
      this.props.partyDetails.members.map(function(member,index) {
        array.push(
          <PartyDetailListRowViewComponent
            id={member.id}
            key={index}
            name={member.name}
            surname={member.surname}
            birthDate={member.birthDate}
            description={member.description}
            numberInParty={member.numberInParty}
          />
        );
      });

    return (
      <div className="panel panel-default">
          <div className="panel-heading">
            <h4 style={{display:'inline', padding:'10, 15'}}><i className="fa fa-table"></i>
              &nbsp; {this.props.partyDetails.name}
            </h4>
            
          </div>
           <table className="table table-striped table-hover">
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
