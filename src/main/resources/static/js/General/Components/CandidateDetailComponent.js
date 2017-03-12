function getCandidateTable(self) {
  var rows = [];
  var info = self.props.info;
  rows.push(<tr><td>{info.name} {info.surname}</td></tr>);
  rows.push(<tr><td>Gimimo data: {info.birthDate}</td></tr>);
  rows.push(<tr><td>Aprašymas: {info.description}</td></tr>);
  if (info.partijosPavadinimas) {
    rows.push(<tr><td>Partija: <a href={'#/party/' + info.partijosId}>{info.partijosPavadinimas}</a></td></tr>);
  }
  if (info.numberInParty) {
    rows.push(<tr><td>Numeris partijos sąraše: {info.numberInParty}</td></tr>);
  }
  if (info.countyName) {
    rows.push(<tr><td>Vienmandatė rinkimų apygarda: <a href={'#/county/' + info.countyId}>{info.countyName}</a></td></tr>);
  }
  if (info.multiList) {
    rows.push(<tr><td>Dalyvauja Daugiamandatėje rinkimų Apygardoje</td></tr>);
  }
  if (!info.multiList && info.partijosPavadinimas != null) {
    rows.push(<tr><td>Nedalyvauja Daugiamandatėje rinkimų Apygardoje</td></tr>);
  }
  return rows;
}
var CandidateDetailComponent = React.createClass({
  render: function() {
    console.log(this.props.info);
    var info = this.props.info;
    var rows = getCandidateTable(this);
    return (
      <div>
      <div className="container">
        <h1 className='yellow'>Kandidatas: {info.name} {info.surname}</h1>
      </div>
      <div id="exTab1" className="container shadow">
        <div className="tab-content clearfix">
          <table width="100%" className="table" id="searchable-table">
            <thead>
              <tr>
                <th>Kandidato Informacija</th>
              </tr>
            </thead>
            <tbody>
              {rows}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    );
  }
});

window.CandidateDetailComponent = CandidateDetailComponent;
