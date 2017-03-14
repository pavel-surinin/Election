var PartyDetailRowViewComponent = React.createClass({
  componentDidMount: function () {
    $(this.refs.info).tooltip();
  },
  render: function() {
    var delta = null;
    if (this.props.delta > 0) {
      delta = <span style={{fontSize : '70%' ,color : 'green'}}> +{this.props.delta} </span>;
    } else if(this.props.delta < 0){
      delta = <span style={{fontSize : '70%',color : 'red'}}> {this.props.delta} </span>;
    } else {
      delta = <span>  </span>;
    }
    var info = null;
    var backgroundColor = styles.partyListStyle(this.props.numberInParty,this.props.mandatesCount);
    return (
      <tr style={backgroundColor} className="small">
        <td>
          {this.props.numberInParty} {delta}
        </td>
        <td>
          <a href={'#/candidate/' + this.props.id}
            ref="info"
            title="Kandidato aprašymas"
            data-toggle="tooltip"
            id={'description-button-' + this.props.id}>
            {this.props.name} {this.props.surname}
          </a>
        </td>
        <td>
          {this.props.birthDate}
        </td>

        <td>
          {this.props.countyName}
        </td>
      </tr>
    );
  }

});

window.PartyDetailRowViewComponent = PartyDetailRowViewComponent;
