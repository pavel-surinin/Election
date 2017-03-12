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
          <a href="#"
            ref="info"
            title="Kandidato aprašymas"
            data-toggle="tooltip"
            data-placement="right"
            data-toggle="modal"
            id={'description-button-' + this.props.id}
            data-target={'#' + this.props.listType + this.props.id}>
            {this.props.name} {this.props.surname}
          </a>
            <div className="modal fade" id={this.props.listType + this.props.id} tabIndex="-1" role="dialog" aria-labelledby="myModalLabel">
              <div className="modal-dialog" role="document">
                <div className="modal-content" style={{margin : 'auto'}}>
                  <div className="modal-header">
                    <button type="button" id="modal-close-button" className="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 className="modal-title">{this.props.name} {this.props.surname}</h4>
                  </div>
                  <div className="modal-body">
                    {this.props.description}
                  </div>
                  <div className="modal-footer">
                    <button type="button" id="close-button" className="btn btn-default btn-sm" data-dismiss="modal">Uždaryti</button>
                  </div>
                </div>
              </div>
            </div>
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
