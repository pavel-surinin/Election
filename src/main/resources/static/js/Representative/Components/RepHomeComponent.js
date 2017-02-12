function succesStyle(bool) {
  if (bool) {
    return {background : '#dff0d8'};
  } else {
    return {background : 'none'};
  }
}
var RepHomeComponent = React.createClass({
  render: function() {
    var single = styles.toggleGreen(this.props.info.resultSingleApproved);
    var multi = styles.toggleGreen(this.props.info.resultMultiApproved);
    console.log(multi);
    return (
      <div>
        <div className="panel panel-default">


              <ul className="list-group">
                 <li className="list-group-item">Atstovas:
                  <span className="text-info">{this.props.info.representativeName}</span></li>
                 <li className="list-group-item">Apygarda :
                  <span className="text-info">{this.props.info.countyName}</span></li>
                 <li className="list-group-item">Apylinkė:
                  <span className="text-info">{this.props.info.name}</span></li>
                 <li className="list-group-item">Adresas:
                  <span className="text-info">{this.props.info.adress}</span></li>
                 <li className="list-group-item">Rinkėjų skaičius:
                  <span className="text-info">{this.props.info.numberOfElectors}</span></li>
                 <li style={single} className="list-group-item">Vienmandatės registravimo laikas:
                  <span className="text-info">{this.props.info.votesSingleRegisteredDate}</span></li>
                 <li style={multi} className="list-group-item">Daugiamandatės registravimo laikas:
                  <span className="text-info">{this.props.info.votesMultiRegisteredDate}</span></li>
              </ul>
        </div>
      </div>
    );
  }

});

window.RepHomeComponent = RepHomeComponent;
