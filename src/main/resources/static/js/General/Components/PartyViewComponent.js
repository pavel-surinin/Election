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
          <div className="container">
            <h1 className='yellow'> Partijų sąrašas</h1>
          </div>
          <div id="exTab1" className="container shadow">
            <div className="tab-content clearfix">
              <table width="100%" className="table table-striped table-hover" id="dataTables-example">
                <thead>
                  <tr>
                    <th className='col-md-2 col-sm-2'>Nr.</th>
                    <th className='col-md-5 col-sm-5'>Partijos pavadinimas</th>
                    <th className='col-md-2 col-sm-2'>Narių skaičius</th>
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
