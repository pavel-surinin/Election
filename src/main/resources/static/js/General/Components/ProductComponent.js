var ProductComponent = React.createClass({
  handleUpdateClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('/update/' + id);
    };
  },

  render: function() {
    return (
        <div>
          <li className='list-group-item'>
          <p>
            <i className='glyphicon glyphicon-book'></i> {this.props.author} - {this.props.title}.<br/>
          </p>
          <p>
            <small>
              <i style={{color: '#4286f4'}} className='glyphicon glyphicon-ok'></i> Published At: <b>{moment(this.props.publishedAt).format('LL')}</b><br/>
              <i style={{color: '#4286f4'}} className='glyphicon glyphicon-barcode'></i> Id <b>{this.props.id}</b>
            </small>
          </p>
            <button onClick={this.handleUpdateClick(this.props.id)} className='btn btn-primary' role='button'>Update book</button>
          </li>
          <br/>
        </div>
    );
  }
});

ProductComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

window.ProductComponent = ProductComponent;
