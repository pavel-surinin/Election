var PartyViewComponent = React.createClass({
  render: function() {
    var self = this;
    var partyArray = [];
    this.props.partyList.map(function(party,index) {
      partyArray.push(
        <PartyViewRowComponent
          id={party.id}
          key={index}
          name={party.name}
          partyNumber={party.partyNumber}
          members={party.members.length}
        />
      );
    });
    return (
        <div>
          <div className="container"><h1>Rinkimuose dalyvaujančių Partijų sąrašas</h1>
          </div>
          <div className="panel panel-default">
            <div className="panel-body">
              <table className="table table-striped">
                <thead>
                  <tr>
                    <th className='col-md-2 col-sm-2'>Nr.</th>
                    <th className='col-md-5 col-sm-5'>Partijos pavadinimas</th>
                    <th className='col-md-2 col-sm-2'>Narių skaičius</th>
                    <th className='col-md-1 col-sm-1'></th>
                  </tr>
                </thead>
                <tbody>
                  {partyArray}
                </tbody>
              </table>
            </div>
          </div>
        </div>
    );
  }
});

window.PartyViewComponent = PartyViewComponent;
