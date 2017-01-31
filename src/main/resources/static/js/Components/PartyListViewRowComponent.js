var  PartyListViewRowComponent= React.createClass({
  handleDetailsClick: function(id){
    var self = this;
    return function() {
      self.context.router.push('/admin/party/' + id);
    };
  },
  
  handleEditClick: function(id){
    var self = this;
	return function () {
	  self.context.router.push('/admin/party/edit/' + id);  
	};
  },
		
  render: function() {
    return (
            <tr>
              <td>
                {this.props.id}
              </td>
              <td>
                {this.props.name}
              </td>
              <td>
                <button onClick={this.handleDetailsClick(this.props.id)} className='btn btn-primary btn-sm' role='button'>Detaliau</button>
              </td>
              <td>
                <button onClick={this.handleEditClick(this.props.id)} type="button" className="btn btn-primary btn-sm fa fa-pencil"></button>
                &nbsp;
                <a href="#/admin/party">
                  <button type="button" className="btn btn-danger btn-sm fa fa-trash"></button>
                </a>
              </td>
            </tr>
    );
  }
});
PartyListViewRowComponent.contextTypes = {
  router: React.PropTypes.object.isRequired,
};
window.PartyListViewRowComponent = PartyListViewRowComponent;
